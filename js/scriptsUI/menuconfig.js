function show_developers(){

	var $thisLi = $("#bt_dev").parent('li');
	var $ul = $thisLi.parent('ul');
	
	if (!$thisLi.hasClass('active')) {
		$ul.find('li.active').removeClass('active');
		$thisLi.addClass('active');
	}
}

function show(){
	$("#teste").show();
	
}
