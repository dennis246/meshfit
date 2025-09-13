
var toggleMode = 1;
var rowNum = 0;

(function () {

    var panelComps = document.getElementsByClassName('#container');
    console.log(panelComps);

    var opGrptags = document.body.querySelectorAll('.op');
    console.log("before",opGrptags);
    for (var j = 0; j < opGrptags.length; j++) {
        opGrptags[j].style.cursor = 'pointer';
        opGrptags[j].addEventListener('click', editPart, false);
       /*  var inIp =  opGrptags[j].querySelector("input");
        inIp.style.cursor = 'cursor'; */
    }

    console.log("after",opGrptags);

    var updateBtn = document.querySelector('#updateBtn');
    updateBtn.addEventListener('click', updateNote);
})();




function editPart() {

    console.log('in editPart', this);
    var parentClassName = this.parentElement.className;
    var className = this.className;
    var invClassName;

    if (className === "op") {
        invClassName = className.replace("op", "ip");
    } else {
        invClassName = className.replace("ip", "op");
    }

    if (toggleMode === 1) {

        this.style.visibility = 'hidden';
        var xiptag = this.parentElement.querySelector('.'+invClassName);
        xiptag.style.visibility = 'visible';
        xiptag.addEventListener('focusout', editPart);
        xiptag.className = invClassName;

    } else {
        this.style.visibility = 'hidden';
        console.log(this.parentElement.parentElement);
        var xopdivtag = this.parentElement.querySelector('.'+invClassName);
        xopdivtag.style.visibility = 'visible';
        var xoptag = xopdivtag.querySelector('p');

        var iptag = this.querySelector(parentClassName === 'header' ? 'input' : 'textarea');
        //xoptag.innerHTML = iptag.value; 
        xoptag.innerHTML = parentClassName === 'header' ? iptag.value : iptag.innerHTML; 
    }

    toggleMode *= -1; 

}



function updateNote() {
    console.log("inside PostContent fn");
    var postTo = '/App1/simplenote';
    var x = document.querySelector('#container');
    console.log(x);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", postTo, true);
    //xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(x.outerHTML);
}