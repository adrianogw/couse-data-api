/**
 * 
 */

var helloApp = angular.module( "helloApp", ['ui.bootstrap'] );

helloApp.service('TopicService', [ '$http', function($http) {

	this.getTopic = function getTopic(topicId) {
		return $http({
			method : 'GET',
			url : 'topics/' + topicId
		});
	}

	this.addTopic = function addTopic(id, name, description) {
		return $http({
			method : 'POST',
			url : 'topics',
			data : {
				id: id,
				name : name,
				description: description
			}
		});
	}

	this.updateTopic = function updateUser(id, name, description) {
		return $http({
			method : 'PUT',
			url : 'topics/' + id,
			data : {
				id : id,
				name : name,
				description: description
			}
		});
	}        

	this.deleteTopic = function deleteTopic(id) {
		return $http({
			method : 'DELETE',
			url : 'topics/' + id
		})
	}  

	this.getAllTopics = function getAllTopics() {
		return $http({
			method : 'GET',
			url : 'topics'
		});
	}        
} ]);    

helloApp.controller('HelloCtrl', ['$scope','TopicService', 
	function ($scope, TopicService) {

	$scope.getTopic = function (id) {
		TopicService.getTopic(id)
		.then(function success(response) {
			$scope.topic = response.data;
			$scope.message='';
			$scope.errorMessage = '';
		},
		function error (response) {
			$scope.message = '';
			if (response.status === 404){
				$scope.errorMessage = 'Topic not found!';
			}
			else {
				$scope.errorMessage = "Error getting topic! Error: "+ response.data.message;
			}
		});
	};

	$scope.resetTopic = function () {
		$scope.topic.id = null;
		$scope.topic.name = null;
		$scope.topic.description = null;
	}

	$scope.addTopic = function () {
		if ($scope.topic != null && $scope.topic.name) {
			TopicService.addTopic($scope.topic.id, $scope.topic.name, $scope.topic.description)
			.then (function success(response){
				$scope.resetTopic();
				$scope.message = 'Topic added!';
				$scope.errorMessage = '';
				$scope.getAllTopics();
			},
			function error(response){
				$scope.errorMessage = 'Error adding topic!';
				$scope.message = '';
			});
		}
		else {
			$scope.errorMessage = 'Please enter a name!';
			$scope.message = '';
		}
	}  

	$scope.updateTopic = function () {
		TopicService.updateTopic($scope.topic.id, 
				$scope.topic.name, $scope.topic.description)
				.then(function success(response) {
					$scope.resetTopic();
					$scope.message = 'Topic data updated!';
					$scope.errorMessage = '';
					$scope.getAllTopics();
				},
				function error(response) {
					$scope.errorMessage = 'Error updating topic!';
					$scope.message = '';
				});
	}  

	$scope.deleteTopic = function (id) {
		TopicService.deleteTopic(id)
		.then (function success(response) {
			$scope.message = 'Topic deleted!';
			$scope.topic = null;
			$scope.errorMessage='';
			$scope.getAllTopics();
		},
		function error(response) {
			$scope.errorMessage = 'Error deleting topic!';
			$scope.message='';
		});
	}   

	$scope.getAllTopics = function () {
		TopicService.getAllTopics()
		.then(function success(response) {
			$scope.topics = response.data;

			$scope.errorMessage = '';
		},
		function error (response) {
			$scope.message='';
			$scope.errorMessage = 'Error getting topics!';
		});
	}  

	$scope.getAllTopics();

}]); 


