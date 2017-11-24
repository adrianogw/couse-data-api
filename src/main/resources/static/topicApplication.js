/**
 * Script containing the service methods and controller methods to support all the CRUD operations for the Topic subject.
 */

//Simply add a dependency to the 'angular-confirm' module coming from angular-confirm.min.
//Simply add a dependency to the 'ui.bootstrap' module coming from ui-bootstrap-tpls-2.5.0.min.
var helloApp = angular.module( "helloApp", ['ui.bootstrap', 'angular-confirm']);


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
				
				//Event fired when the dialog is rendered.
				modalInstance.rendered.then(function() {
					if (!selectedTopic || !selectedTopic.id)
						document.getElementById("id").focus();
					else
						document.getElementById("name").focus();

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

helloApp.controller('ModalInstanceCtrl', function ($uibModalInstance, currentTopic) {
	
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
		
		if (!$ctrl.topic || !$ctrl.topic.id)
		{
			$ctrl.errorMessage = 'Please, specify a topic ID!';	
			document.getElementById("id").focus();
		}
		
		if (!$ctrl.topic.name)
		{
			$ctrl.errorMessage = 'Please, specify a topic name!';	
			document.getElementById("name").focus();
		}

		if (!$ctrl.topic.description)
		{
			$ctrl.errorMessage = 'Please, specify a topic description!';	
			document.getElementById("description").focus();
		}
	};
	
	
});
