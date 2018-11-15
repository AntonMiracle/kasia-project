// registration timeZone
var getTimeZone = function () {
    var timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;
    document.getElementById("registrationFormId:browserTimeZome").value = timeZone;
}
