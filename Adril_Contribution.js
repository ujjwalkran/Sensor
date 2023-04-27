# Adril Vaz Contribution


I have fetched the value from the firebase and streamed same to the website. [Niyata N Naik](https://github.com/NiyataNaik) helped me in this. We both worked on this together.


var firebaseConfig = {
  			apiKey: "AIzaSyD77O_xaQmg3tV9kKHfne1Zs4pGLfsULas",
  			authDomain: "sensorprojects-6db23.firebaseapp.com",
  		  databaseURL: "https://sensorprojects-6db23-default-rtdb.firebaseio.com",
 			projectId: "sensorprojects-6db23",
  		storageBucket: "sensorprojects-6db23.appspot.com",
 			 messagingSenderId: "407244447376",
 			 appId: "1:407244447376:web:8067aa0ecce952a4a98215",
  		measurementId: "G-38DTS587G4"
			};

firebase.initializeApp(firebaseConfig);
//var firebaseRef = firebase.databse().ref("")
var database = firebase.database();
var allValuesRef = database.ref('Sensorvalue');
var accelDataRef = allValuesRef.child('acclerometer');
var gyroDataRef = allValuesRef.child('gyroscope9');
var accelXRef = accelDataRef.child('x');
var accelYRef = accelDataRef.child('y');
var accelZRef = accelDataRef.child('z');

var gyroXRef = gyroDataRef.child('x1');
var gyroYRef = gyroDataRef.child('x2');
var gyroZRef = gyroDataRef.child('x3');
var gyroZxRef = gyroDataRef.child('x4');

accelXRef.once('value',function(snapshot) {
  var accelXValue=snapshot.val();
  document.write("Accelerometer");
  document.write("X : "+accelXValue);
  
});

accelYRef.once('value',function(snapshot) {
  var accelYValue = snapshot.val();
  // Do something with the accelYValue
 
document.write("Y : "+accelYValue);

});

accelZRef.once('value',function(snapshot) {
  var accelZValue = snapshot.val();
  // Do something with the accelZValue
document.write("Z : "+accelZValue);
});

gyroXRef.once('value',function(snapshot) {
  var gyroXValue = snapshot.val();
  // Do something with the gyroXValue
  document.write("Gyrometer");
document.write("X : "+gyroXValue);
});

gyroYRef.once('value',function(snapshot) {
  var gyroYValue = snapshot.val();
document.write("Y : "+gyroYValue);
});

gyroZRef.once('value',function(snapshot) {
  var gyroZValue = snapshot.val();
 document.write("Z : "+gyroZValue);
});
