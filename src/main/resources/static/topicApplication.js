/**
 * Script containing the service methods and controller methods to support all the CRUD operations for the Topic subject.
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

helloApp.controller('HelloCtrl', ['$scope','TopicService', '$uibModal', '$log', '$document',
	function ($scope, TopicService, $uibModal, $log, $document) {

	$scope.topic = null;
	
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
		$scope.topic = null;
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

	//function to open a modal dialog. The controller for the modal dialog is ModalInstanceCtrl (specified below).
	$scope.openModalDialog = function (crudAction, selectedTopic, size, parentSelector) {
		
		var parentElem = parentSelector ? 
				angular.element($document[0].querySelector('.modal-demo ' + parentSelector)) : undefined;

				var modalInstance = $uibModal.open({
					animation: 'true',
					ariaLabelledBy: 'modal-title',
					ariaDescribedBy: 'modal-body',
					templateUrl: 'myModalContent.html',
					controller: 'ModalInstanceCtrl',
					controllerAs: '$ctrl', //Alias that must be used in the HTML code for the modal dialog template
					size: size,
					appendTo: parentElem,
					resolve: {
						//This function is passed as a parameter to the modal dialog controller.
						currentTopic: function () {
							return selectedTopic;
						}
					}
				});

				//Called when the modalInstance is closed. In our case here, it is fired by the $ctrl.ok inside the ModalInstanceCtrl
				modalInstance.result.then(function (topicFilled) {

					$scope.topic = topicFilled;
					
					if (crudAction == 'POST')
					{
						$scope.addTopic();
					}
					else
					{
						$scope.updateTopic();
					}

				}, function () {
					$log.info('Modal dismissed at: ' + new Date());
				});
	};	

}]); 

/**
 * Modal dialog controller. It is not the same controller above. It is exclusive to control the dialog models.
 */

angular.module('ui.bootstrap').controller('ModalInstanceCtrl', function ($uibModalInstance, currentTopic) {
	
	var $ctrl = this;

	//Clone the object to not change the currentTopic directly.
	$ctrl.topic =  angular.copy(currentTopic);
	$ctrl.isUpdating = $ctrl.topic && $ctrl.topic.id; 
	$ctrl.errorMessage = '';
	
	$ctrl.ok = function () {
		
		$ctrl.validateFields();
		
		if (!$ctrl.errorMessage)
		{
			$uibModalInstance.close($ctrl.topic);
		}
	};

	$ctrl.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};

	$ctrl.validateFields = function () {
		
		$ctrl.errorMessage = null;
		
		if (!$ctrl.topic.id)
		{
			$ctrl.errorMessage = 'Please, specify a topic ID!';	
		}
		
		if (!$ctrl.topic.name)
		{
			$ctrl.errorMessage = 'Please, specify a topic name!';	
		}

		if (!$ctrl.topic.description)
		{
			$ctrl.errorMessage = 'Please, specify a topic description!';	
		}
	};
	
	
});
