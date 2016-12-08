$(".answer-write input[type='submit']").click(addAnswer);
$(".qna-comment-slipp-articles").on("click", "delete-answer-form button[type='submit']" ,delAnswer);


//$(".link-delete-article").click(delAnswer);


function addAnswer(e){
	console.log('click answer button!');
	e.preventDefault();
	
	var url = $(".answer-write").attr("action");
	console.log("url : " + url);
	
	var queryString = $(".answer-write").serialize();
	console.log("queryString : " + queryString);
	
	//ajax
	$.ajax({
		type: 'post',
		url: url,
		data: queryString,
		dataType: 'json',
		error: function(){
			console.log('fail!');
		},
		success:function(data){
			console.log('data', data);
			var answerTemplate = $("#answerTemplate").html(); 
			var template = answerTemplate.format(data.writer.userId, data.formattedCreateDate, data.contents, data.question.id, data.id);      
			$(".qna-comment-slipp-articles").prepend(template);                      
			$("textarea[name=contents]").val(""); 
		}
	});	
}

function delAnswer(e){
	console.log('click delete button!');
	e.preventDefault();
	
	var url = $(".form-delete-answer").attr("action");
	console.log("url"+url);
	
	var deleteBtn = $(this);
	
	$.ajax({
		type: 'delete',
		url: url,
		dataType: 'json',
		error: function(){
			console.log('fail!');
		},
		success:function(data){
			console.log('data', data);
			
			deleteBtn.closest(".article").remove();
		}
	});
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};