<h1 align="center">Instabug Android Task</h1>

<p align="center">
Android Coding Task For Instabug



# Overview

When I got the task and while reading it first I started off thinking, "Piece of cake!" But then, BAM! The notes hit me "No third-party libraries allowed". 

It was a really interesting task i loved creating the app and the challenges I faced 

# Tech Stacks

- MVVM Architecture: The Arch promotes reusability of code, greatly simplifying the process of creating simple user interfaces
- Jetpack Compose: Using Modern Tech of Jetpack Compose.
- httpUrlConnection: Used hilt for Handling all requests.
- Sqlite: Used to cache responses and requests.

# Api Service 

As I got started, my main concern was figuring out how to make requests without Retrofit. So with simple search showed me that I could use HttpUrlConnection instead.
So Now let's start to figure out how the HTTPURLConnection works: 

1. Create an Url Object from our Input Url : 
```kotlin
    val url = URL(inputUrl.toString())
```
2. Open Connection: Using the openConnection() method of the URL object, 
```kotlin
    httpURLConnection = url.openConnection() as HttpURLConnection
```
3. Set Request Method & Request Headers (Optional): Default Request Type is GET

5. Now we come with the part of sending and receiving data , if we want to send data we get the output stream from the connection and if we want to receive data we get the input stream from the connection.

6. Now the request is done then we map results to our API response model

7. Finally we close the connection
```kotlin
    httpurlconnection.disconnect()
```
*If you Wondered like me at first where actually the connection starts I got you, after a quick search I found that httpurlconnection starts the connection when we try to get stream for receiving or sending data*

As Simple as that I finished Creating 3 methods for requests one for GET Requests, POST with JSON Body and one for File UPLOAD

# Caching and Database 
As mentioned in the task description we are not allowed to use room so what came to my mind first creating caching with SQLite Database I created 3 classes 

1. Table Object containing all data about database like :(Db name, Columns names, Table Creation and Dropping Query)
   
2. Model data class for our database objects and mapping functions to our domain object.
   
3. Database Helper Class for executing all Queries and filtering data from the database. 

# Domain 
Since We are Done Now with our data layer it turns to create a domain layer which is simple now:

1. I Created a data class for responses for the domain model which we will use in our app layer
2. Repository Interface containing all necessary functions

# Viewmodel 
I created view models based on events and states each View model contains a state so as simple as that if any event occurs in the UI like user interaction or whatever we update the state with changes
so for each view model, I created one data class for handling states and one sealed class for holding all possible events in the app.

*I have two challenges, in that case, how am gonna run repo functions in the background thread (As we are doing tasks that could take a long time to execute so it can block our UI Thread ) if i can do so, how i am gonna interact from the thread to update our Ui with new state*

So After a search, I found a way to run a block in the background thread which is *Executors* in Java 

1. So I created  an instance of executor and function to submit any block of code in it.
```kotlin
private val executors = Executors.newCachedThreadPool()

fun runInBackground(block: () -> Unit) {
  executors.submit(block)
}
```
2. And for the UI thread I created uiHanlder this allows us to post and process messages on the main thread's message queue, which is essential for updating the UI from background threads.
```kotlin
private val uiHandler = Handler(Looper.getMainLooper())

fun runInUiThread(block: () -> Unit) {
  uiHandler.post(block)
}

```

# UI Screens 

I built the UI with Jetpack compose I built only 2 screens one for executing requests and one for listing all of them 

|                   Main Screen                        |                   Logs Screen                      |                     
|:----------------------------------------------------:|:--------------------------------------------------:|
|     ![Main Screen](mainscreen.png)                   |             ![Logs](LogsScreen.png)                | 










