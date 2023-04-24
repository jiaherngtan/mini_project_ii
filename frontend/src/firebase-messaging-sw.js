importScripts('https://www.gstatic.com/firebasejs/9.20.0/firebase-app-compat.js');
importScripts('https://www.gstatic.com/firebasejs/9.20.0/firebase-messaging-compat.js');

firebase.initializeApp({
    projectId: 'jh-mini-project',
    appId: '1:108604441138:web:7b7542ad7e119401acf22b',
    storageBucket: 'jh-mini-project.appspot.com',
    apiKey: 'AIzaSyBHuRICxDNMEQ2WENF4CkEAPipQGBYdNUo',
    authDomain: 'jh-mini-project.firebaseapp.com',
    messagingSenderId: '108604441138',
    measurementId: 'G-MKPX6BJ1FG',
})
const messaging = firebase.messaging();