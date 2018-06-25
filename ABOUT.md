# MQTTApp Test
Android application on the MQTT, The MQTT connection is encapsulated within an Android-Service that runs in the background of the Android application.


## My impressions and experience with the Project

### Languages
```
Java for Controling
XML  for the Design
```
#### Why I chose These Languages

I used Java cause I'm more familiar with it than Kotlin, though I know How to code in Kotlin,
but java is more efficient i think.
other reason is that the Documentation and the support for this type of project is in Java.

And I used XML to design the simple, one paged, app.

#### WHY NATIVE ANDROID APPROACH
Because this kind of apps that integrates with IOT systems are very simple and the Libraries exists for it, so you can easly use them instead of making them on your own.

Also native is faster than other frameworks.

### What I wanted to accomplish
I was introduced to a new field in the android programming, that is IOT with android.

so I wanted to get the most out of it, as It's my field of study in the first place(IOT).

### Thought Process
[note]: usually I draw it on paper but I'll try to descripe it.

I wanted to make the app as simple as possible, so I made it with only one activity and one layout file.
as you'll see in the Design , there is a menu elements, two items actually, one to connect and the other one to disconnect, when pressing on "connect" if the connection isn't successful
a toast, small message, shall appear to the user to check the MQTT service and the Brocker etc.
if the connection is successful another layout will appeare under the existing one showing the two switches, Buttons, one to say hello, the other to say bye
and a TextView, to show text, to view the topic messages.
when pressing disconnect, the Layout, in which the two switches and the Text are, will vanish But only if the disconnection process is successful, also there are Two Toasts to indicate if the disconnection succeeded or not.

### What I Learned
I got to know more about IOT Protocols with Android and other platforms.

Also I got to know the MQTT and work with it

### My expreience
Java (Intermediate)

Kotlin (Beginner)

Android in general (I've been learning it and working with it for a year so far)


## Authors

* **Hady walied** - *Initial work* - [Hady Walied](https://github.com/hadywalied)

