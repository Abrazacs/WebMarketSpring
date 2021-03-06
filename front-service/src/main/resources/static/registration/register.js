angular.module('market').controller('registerController', function ($scope, $http) {

    $scope.tryToSignIn = function () {
        if ($scope.entrant.username == '' || $scope.entrant.password == '' ||
            $scope.entrant.email == '' || $scope.entrant.confirmation == ''){
            alert('Необходимо заполнить все поля');
            return;
        }
        else if ($scope.entrant.password != $scope.entrant.confirmation){
            alert('Пароли должны совпадать');
            return;
        }
        else{
            $http.post('http://localhost:5555/auth/registration', $scope.entrant)
                .then(function successCallback(response) {
                    alert('Вы успешно зарегистрировались. Войдите под учетной записью')
                    },
                function errorCallback(response){
                    $scope.errorData = response.data;
                    alert($scope.errorData.message)
                });
        }

    };

});