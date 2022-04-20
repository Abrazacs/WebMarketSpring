angular.module('market', []).controller('indexController', function ($scope, $http) {
    $scope.fillTable = function () {
        $http.get('http://localhost:8180/market/api/v1/products')
            .then(function (response) {
                $scope.products = response.data;
            });
    }

    $scope.fillCart = function (){
        $http.get('http://localhost:8191/market-cart/api/v1/cart')
            .then(function (response){
                $scope.productsInCart = response.data;
            });
    }

    $scope.deleteProduct = function (id) {
        $http.delete('http://localhost:8180/market/api/v1/products/' + id)
            .then(function (response) {
                $scope.fillTable();
            });
    }

    $scope.createNewProduct = function () {
        $http.post('http://localhost:8180/market/api/v1/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.fillTable();
            });
    }

    $scope.addProduct = function (id){
        $http.get('http://localhost:8191/market-cart/api/v1/cart/add-to-cart/'+id)
            .then(function (response){
                $scope.fillCart();
            });
    }

    $scope.increment = function (id){
        $http.get('http://localhost:8191/market-cart/api/v1/cart/increment/'+id)
            .then(function (response){
                $scope.fillCart();
            });
    }

    $scope.decrement = function (id){
        $http.get('http://localhost:8191/market-cart/api/v1/cart/decrement/'+id)
            .then(function (response){
                $scope.fillCart();
            });
    }

    $scope.clearCart = function (){
        $http.get('http://localhost:8191/market-cart/api/v1/cart/clear')
            .then(function (response){
                $scope.fillCart();
            });
    }

    $scope.fillTable();
    $scope.fillCart();
})