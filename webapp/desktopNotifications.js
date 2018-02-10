
// var notification;
// var key = "AIzaSyDxleQy4Dys84cWJjV90fwqFqt24fImM1Q";
// var to = 'YOUR-IID-TOKEN';

// // Retrieve Firebase Messaging object.
// const messaging = firebase.messaging();
// // messaging.requestPermission()
// // .then(function() {
// //   console.log('Notification permission granted.');
// //   // TODO(developer): Retrieve an Instance ID token for use with FCM.
// //   // ...
// // })
// // .catch(function(err) {
// //   console.log('Unable to get permission to notify.', err);
// // });




// window.onload = function() {
//     Notification.requestPermission();
// }

// /*
// notificationObject structure:

// Notification.badge
//     The URL of the image used to represent the notification when there is not enough space to display the notification itself.

// Notification.body
//     The body string of the notification as specified in the options parameter of the constructor.

// Notification.icon
//     The URL of the image used as an icon of the notification as specified in the options parameter of the constructor.
// Notification.image
//     The URL of an image to be displayed as part of the notification, as specified in the options parameter of the constructor.
// Notification.renotify
//     Specifies whether the user should be notified after a new notification replaces an old one.
// Notification.requireInteraction
//     A Boolean indicating that a notification should remain active until the user clicks or dismisses it, rather than closing automatically.
// Notification.silent
//     Specifies whether the notification should be silent, i.e. no sounds or vibrations should be issued, regardless of the device settings.
// Notification.timestamp
//     Specifies the time at which a notification is created or applicable (past, present, or future).
// Notification.title
//     The title of the notification as specified in the first parameter of the constructor.
// Notification.vibrate
//     Specifies a vibration pattern for devices with vibration hardware to emit.

// Event handlers
// Notification.onclick
//     A handler for the click event. It is triggered each time the user clicks on the notification.
// Notification.onerror
//     A handler for the error event. It is triggered each time the notification encounters an error.

// */

var testNotification = {
    'title': 'Portugal vs. Denmark',
    'body': '5 to 1',
    'icon': 'firebase-logo.png',
    'click_action': 'http://localhost:8081'
};

createNotification = function(notificationObject) {
    // var notification = {
    //   'title': notificationObject.title,
    //   'body': notificationObject.body,
    //   'icon': notificationObject.icon
    // };
    
    // fetch('https://fcm.googleapis.com/fcm/send', {
    //   'method': 'POST',
    //   'headers': {
    //     'Authorization': 'key=' + key,
    //     'Content-Type': 'application/json'
    //   },
    //   'body': JSON.stringify({
    //     'notification': notification,
    //     'to': to
    //   })
    // }).then(function(response) {
    //   console.log(response);
    // }).catch(function(error) {
    //   console.error(error);
    // })
    var notification = new Notification("TEst");
}

notifyMe = function() {
    var notificationObject = testNotification;
    // Let's check if the browser supports notifications
    if (!("Notification" in window)) {
        console.log("This browser does not support desktop notification");
    }
    // Let's check whether notification permissions have already been granted
    else if (Notification.permission === "granted") {
        // If it's okay let's create a notification
        createNotification(notificationObject);
    }
    // Otherwise, we need to ask the user for permission
    else if (Notification.permission !== "denied") {
        Notification.requestPermission().then(
            function (permission) {
                // If the user accepts, let's create a notification
                if (permission === "granted") {
                    createNotification(notificationObject);
                }
            }
        );
    }
}