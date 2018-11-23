
// registration timeZone
var getTimeZone = function (id) {
    var timeZone = Intl.DateTimeFormat().resolvedOptions().timeZone;
    document.getElementById(id).value = timeZone;
}