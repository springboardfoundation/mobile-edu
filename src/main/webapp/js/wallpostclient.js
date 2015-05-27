/*  Java Script page to submit expert answer to the student asking the question
 *   Developed By: Md Mustaqeem
 *   Developed On: 25 April 2015
 *  
 */

var postApiUrl = '/api/WallPost/', commentApiUrl = '/answer/postAnswer/';

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

	self.newAnswer = ko.observable();
	self.addAnswer = function() {
		console.log("Entered Method addComment");
		var expert = new ExpertAnswer();
		expert.AnsweId = self.AnsweId;
		expert.answer(self.newAnswer());
		expert.expertName = 'Guest';
		expert.expertId = '-1';
		expert.answerDate = getTimeAgo(new Date().toISOString());
		self.answersList.push(expert);
		self.newAnswer('');
		$.post(Constants.ExpertRestURL + "saveAnswer", this, handleAnsReply);
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
}

function handleAnsReply() {

};

function ExpertAnswer(data) {
	var self = this;
	data = data || {};

	// Persisted properties
	self.answerId = data.answerId;
	self.answer = ko.observable(data.answer || "");
	self.expertId = data.expertId || "";
	self.expertName = data.expertName || "";
	self.answerDate = getTimeAgo(data.answerDate);
	self.error = ko.observable();
	// persist edits to real values on accept
	self.deleteComment = function() {
	}

}

function getTimeAgo(varDate) {
	if (varDate) {
		return $.timeago(varDate.toString().slice(-1) == 'Z' ? varDate
				: varDate + 'Z');
	} else {
		return '';
	}
}

function loadAllQuestions() {
	var quesUrl = Constants.QuestionURL + "allQuestions";
	console.log("inside loadAllQuestions():" + quesUrl);
	$.get(quesUrl, display);
};
function display(dbData){
	ko.applyBindings(new showQuestions(dbData));
}

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
	
}

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

// custom bindings

// textarea autosize
ko.bindingHandlers.jqAutoresize = {
	init : function(element, valueAccessor, aBA, vm) {
		if (!$(element).hasClass('msgTextArea')) {
			$(element).css('height', '1em');
		}
		$(element).autosize();
	}
};

ko.applyBindings(new viewModel());
