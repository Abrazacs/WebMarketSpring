angular.module('market').controller('regController', function ($scope, $http) {

    $scope.singIn =function (){
        $http.post('http://localhost:5555/auth/registration', $scope.newUser)
            .then(function (response){
                $scope.status = response.data
        })
    };

    $scope.isStatusNull = function (){
        if ($scope.status == null) {
            return true;
        } else {
            return false;
        }
    };

});