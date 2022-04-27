angular.module('market', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {

    if ($localStorage.marchMarketUser) {
        try {
            let jwt = $localStorage.marchMarketUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp) {
                console.log("Token is expired!!!");
                delete $localStorage.marchMarketUser;
                $http.defaults.headers.common.Authorization = '';
            }
        } catch (e) {
        }

        if ($localStorage.marchMarketUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marchMarketUser.token;
        }
    }

    $scope.tryToAuth = function () {
        $http.post('http://localhost:5555/auth/authenticate', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.marchMarketUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
    };

    $scope.clearUser = function () {
        delete $localStorage.marchMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.isUserLoggedIn = function () {
        if ($localStorage.marchMarketUser) {
            return true;
        } else {
            return false;
        }
    };



    $scope.fillTable = function () {
        $http.get('http://localhost:5555/core/api/v1/products')
            .then(function (response) {
                $scope.products = response.data;
            });
    }

    $scope.fillCart = function (){
        $http.get('http://localhost:5555/cart/api/v1/cart/items')
            .then(function (response){
                $scope.productsInCart = response.data;
            });
    }

    $scope.deleteProduct = function (id) {
        $http.delete('http://localhost:5555/core/api/v1/products/' + id)
            .then(function (response) {
                $scope.fillTable();
            });
    }

    $scope.createNewProduct = function () {
        $http.post('http://localhost:5555/core/api/v1/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.fillTable();
            });
    }

    $scope.addProduct = function (id){
        $http.get('http://localhost:5555/cart/api/v1/cart/add-to-cart/'+id)
            .then(function (response){
                $scope.fillCart();
            });
    }

    $scope.increment = function (id){
        $http.get('http://localhost:5555/cart/api/v1/cart/increment/'+id)
            .then(function (response){
                $scope.fillCart();
            });
    }

    $scope.decrement = function (id){
        $http.get('http://localhost:5555/cart/api/v1/cart/decrement/'+id)
            .then(function (response){
                $scope.fillCart();
            });
    }

    $scope.clearCart = function (){
        $http.get('http://localhost:5555/cart/api/v1/cart/clear')
            .then(function (response){
                $scope.fillCart();
            });
    }

    $scope.fillTable();
    $scope.fillCart();
})