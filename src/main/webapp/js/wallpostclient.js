/*  Java Script page to submit expert answer to the student asking the question
 *   Developed By: Md Mustaqeem
 *   Developed On: 25 April 2015
 *  
 */
// Variable to store the answer being typed
var message;




var initData = [ {
	"questionId" : 1,
	"question" : "What I should study after 10th?",
	"questionDate" : "2015-01-01T17:16:12.9037612",
	"answersList" : [ {
		"answerId" : "1",
		"expertId" : "1",
		"expertName" : "Musu",
		"answer" : "I too don't know",
		"answerDate" : "2015-04-01T17:16:12.9037612"
	} ]
} ]

// Model
function Question(data) {
	var self = this;
	// Global expert to push after save is success
	var expertAns = new ExpertAnswer();

	console.log("inside question")
	data = data || {};
	console.log("questionid:"+data.questionId)
	self.questionId = data.questionId;
	
	self.question = data.question;
	console.log("question:"+data.question)
	
	self.questionDate = getTimeAgo(data.questionDate);
	console.log("questionDate:"+data.questionDate)

	self.error = ko.observable();
	self.answersList = ko.observableArray();

	self.expertAnswer = ko.observable();
	
	self.setValue = function(expertAnswer){
		alert("Answer:" + expertAnswer);
	};
	self.addAnswer = function() {
		var some = self.expertAnswer;
		console.log("Entered Method added comment:" + message);

		expertAns.questionId = self.questionId;
		// New answer and hence id is -1
		expertAns.answerId = -1;
		// expert.answer(self.expertAnswer());
		expertAns.answer = message;
		expertAns.expertName = exp_name;
		expertAns.expertId = exp_id;
		expertAns.answerDate = new Date().toISOString();
		// getTimeAgo(new Date().toISOString());
		console.log("The expert answer:" + expertAns);

		var url = Constants.AnswerURL + "replyToQuestion";
		console.log("URL =" + url);
		makeJsonAjaxPostCall(url, expertAns, self.handleAnsReply);
	}
	if (data.answersList) {
		var mappedPosts = $.map(data.answersList, function(item) {
			return new ExpertAnswer(item);
		});
		self.answersList(mappedPosts);
	}
	self.toggleComment = function(item, event) {
		$(event.target).next().find('.publishComment').toggle();
	}

	self.handleAnsReply = function(status) {
		if(status === 'Success'){
		alert("Your answer has been saved successfully:" + status);
		expertAns.answerDate = getTimeAgo(expertAns.answerDate);
		self.answersList.push(expertAns);
		self.expertAnswer('');
	}
		else alert ("Error while sending answer :" +status);
	}
};

function ExpertAnswer(data) {
	var self = this;
	data = data || {};

	// Persisted properties
	self.questionId=data.questionId;
	self.answerId = data.answerId;
	self.answer = ko.observable(data.answer || "");
	self.expertId = data.expertId || "";
	self.expertName = data.expertName || "";
	self.answerDate = data.answerDate;
	self.error = ko.observable();
	// persist edits to real values on accept
	self.deleteComment = function() {
	}

};


function ExperObj(data) {
	var self = this;
	data = data || {};

	// Persisted properties
	self.questionId=data.questionId;
	self.answerId = data.answerId;
	self.answer = data.answer || "";
	self.expertId = data.expertId || "";
	self.expertName = data.expertName || "";
	self.answerDate = new Date().toISOString();
};


function getTimeAgo(varDate) {
	if (varDate) {
		return $.timeago(varDate.toString().slice(-1) == 'Z' ? varDate
				: varDate + 'Z');
	} else {
		return '';
	}
};


var expName, expId, expLogin;

function validateAndLoad() {
	if (setSession) {
		console.log("id :" + getExpId() + " login id :" + getExpLoginId());
		console.log("Calling setSession");
		setSession();
		console.log("id :" + getExpId() + " login id :" + getExpLoginId());

	}
	if (getExpId() != 0 || getExpLoginId() != "guest"
			|| getExpName() != "Guest") {
		loadAllQuestions();
	} else {
		console.log("befor perform logout");
		performLogOut();

	}
}

function loadAllQuestions() {
	 var quesUrl = Constants.QuestionURL + "allQuestions";
	console.log("inside loadAllQuestions():" + quesUrl);
	$.get(quesUrl, display);
};
function display(dbData){
	var element = $('#quesHolder'); 
	ko.cleanNode(element);
	ko.applyBindings(new showQuestions(dbData));
};

//custom bindings


function showQuestions(dbData) {
	var self = this;
	console.log("showQuestions(dbData) data:" + dbData);

	self.questionsList = ko.observableArray();
	self.error = ko.observable();
	self.loadPosts = function() {
		// To load existing posts
		var mappedPosts = $.map(dbData, function(item) {
			return new Question(item);
		});
		self.questionsList(mappedPosts);
	}
	self.loadPosts();

	self.getUnasnwered = function(){
		var quesUrl = Constants.QuestionURL + "unAnsweredQues";
		console.log("unAnswered filter clicked" + quesUrl);
		for(;self.questionsList.pop(););
		$.get(quesUrl, function(newList){
			var mappedPosts = $.map(newList, function(item) {
				return new Question(item);
			});
			self.questionsList(mappedPosts);
		});
		}
	self.getAnsnwered = function(){
		var quesUrl = Constants.QuestionURL + "AnsweredQues";
		console.log("unAnswered filter clicked" + quesUrl);
		for(;self.questionsList.pop(););
		$.get(quesUrl, function(newList){
			var mappedPosts = $.map(newList, function(item) {
				return new Question(item);
			});
			self.questionsList(mappedPosts);
		});
	}
	self.getAllQuestions = function(){
		var quesUrl = Constants.QuestionURL + "allQuestions";
		console.log("All Questions filter clicked" + quesUrl);
		for(;self.questionsList.pop(););
		$.get(quesUrl, function(newList){
			var mappedPosts = $.map(newList, function(item) {
				return new Question(item);
			});
			self.questionsList(mappedPosts);
		});
	}
	
	self.getMyReplies = function(){
		var quesUrl = Constants.QuestionURL + "myAnsweredQues";//current expert replied question
		console.log("unAnswered filter clicked" + quesUrl);
		for(;self.questionsList.pop(););
		$.get(quesUrl, function(newList){
			var mappedPosts = $.map(newList, function(item) {
				return new Question(item);
			});
			self.questionsList(mappedPosts);
		});
		}

};


// textarea autosize
ko.bindingHandlers.jqAutoresize = {
	init : function(element, valueAccessor, aBA, vm) {
		if (!$(element).hasClass('msgTextArea')) {
			$(element).css('height', '1em');
		}
		$(element).autosize();
	}
};



ko.bindingHandlers.maxLength = {
        update: function(element, valueAccessor, allBindings){
        	// console.log("inside limiting function");
        	if(allBindings().value()){
        		element.value = element.value.substr(0, valueAccessor());
                allBindings().value(allBindings().value().substr(0, valueAccessor()));
                message=allBindings().value().substr(0, valueAccessor());
            }
        }
    }
function performLogOut(){
	console.log("inside performLogOut")
	setExpId(0);
	setExpName('guest');
	setExpLoginId('Guest');
	var url = window.location.origin+"/expertlogin.html";
	console.log("Redirect url:"+url);
	window.location.assign(url);
}




/*
function viewModel() {
	var self = this;
	self.questionsList = ko.observableArray();
	self.error = ko.observable();
	self.loadPosts = function() {
		// To load existing posts
		console.log("initData:" + initData);
		var mappedPosts = $.map(initData, function(item) {
			return new Question(item);
		});
		self.questionsList(mappedPosts);
	}
	self.loadPosts();
	return self;
};
ko.applyBindings(new viewModel());
*/