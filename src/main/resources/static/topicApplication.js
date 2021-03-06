/**
 * Script containing the service methods and controller methods to support all the CRUD operations for the Topic subject.
 */

//Simply add a dependency to the 'angular-confirm' module coming from angular-confirm.min.
//Simply add a dependency to the 'ui.bootstrap' module coming from ui-bootstrap-tpls-2.5.0.min.
var helloApp = angular.module( "helloApp", ['ui.bootstrap', 'angular-confirm']);

//It is necessary to disable the cache in order to make the rest calls work on IE.
helloApp.config(['$httpProvider', function($httpProvider) {
    if (!$httpProvider.defaults.headers.common) {
        $httpProvider.defaults.headers.common = {};
    }
    $httpProvider.defaults.headers.common["Cache-Control"] = "no-cache";
    $httpProvider.defaults.headers.common.Pragma = "no-cache";
    $httpProvider.defaults.headers.common["If-Modified-Since"] = "0";
}]);

helloApp.service('TopicService', [ '$http', function($http) {

	var REST_PATH = 'rest/topics';
	var REST_API_VERSION = 'application/vnd.agw.api.v1+json';
	var REST_API_DTO_TYPE = 'TopicDto';
	
	this.getTopic = function getTopic(topicId) {
		return $http({
			method : 'GET',
			headers: {'Accept': REST_API_VERSION},
			url : REST_PATH + '/' +  topicId
		});
	}

	this.addTopic = function addTopic(id, name, description) {
		return $http({
			method : 'POST',
			headers: {'Accept': REST_API_VERSION},
			url : REST_PATH,
			data : {
				type: REST_API_DTO_TYPE,
				id: id,
				name : name,
				description: description
			}
		});
	}

	this.updateTopic = function updateTopic(id, name, description) {
		return $http({
			method : 'PUT',
			headers: {'Accept': REST_API_VERSION},
			url : REST_PATH + '/' + id,
			data : {
				type: REST_API_DTO_TYPE,
				id : id,
				name : name,
				description: description
			}
		});
	}        

	this.deleteTopic = function deleteTopic(id) {
		return $http({
			method : 'DELETE',
			headers: {'Accept': REST_API_VERSION},
			url : REST_PATH + '/' + id
		})
	}  

	this.getAllTopics = function getAllTopics() {
		return $http({
			method : 'GET',
			headers: {'Accept': REST_API_VERSION},
			url : REST_PATH
		});
	}        
	
	this.getRabbitMqCredentials = function getRabbitMqCredentials(authToken) {
		
		return $http({
			method : 'GET',
			headers: {'Accept': REST_API_VERSION},
			url : REST_PATH + '/authRabbitMq/' + authToken
		});
	}
	
} ]);    

helloApp.controller('HelloCtrl', ['$scope','TopicService', '$uibModal', '$log', '$document',
	function ($scope, TopicService, $uibModal, $log, $document) {

	$scope.topic = null;
	
	//TO-DO: There is no rest API to generate a token yet.
	$scope.authToken = 'fakeToken';
	
	$scope.getTopic = function (id) {
		TopicService.getTopic(id).then(
		function success(response) {
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
				//$scope.getAllTopics();
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
					//$scope.getAllTopics();
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
			//$scope.getAllTopics();
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

	$scope.configureRabbitMqStompReceiver = function (rabbitCredentials) {

		// Stomp.js boilerplate
		var ws = new WebSocket('ws://' + window.location.hostname + ':15674/ws');
	    var client = Stomp.over(ws);

		var on_connect = function() {
			
			id = client.subscribe('/topic/queueTopics', on_message);				  
		};

		var on_message = function(data) {
			
		 	console.log('Message received!');
  	  	 	var resourceStateMessage = JSON.parse(data.body);
  	  	 	console.log(resourceStateMessage);
  	  	 	$scope.getAllTopics();
		};

		var on_error = function(e) {
			
		  alert("Socket error "+e);  
		  console.log('on_error');
		};

		client.connect(rabbitCredentials.login, rabbitCredentials.passcode, on_connect, on_error, '/');
	}  

	$scope.createRabbitMqStompReceiver = function () {
		
 	    console.log('Creating RabbitMqStompReceiver...');
		TopicService.getRabbitMqCredentials($scope.authToken).then(
		function success(response) {
	 	    console.log('Configuring RabbitMqStompReceiver...');
			$scope.configureRabbitMqStompReceiver(response.data);
		},
		function error (response) {
			$scope.errorMessage = 'Error getting RabbitMQ credentials!';
		});
	}
	
	$scope.getAllTopics();
	
	$scope.createRabbitMqStompReceiver();

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
