# Listen Hear

A system meant to visualize sounds that might otherwise go unnoticed in a deaf person's life. **CRASH** - **BOOM** - **CRACK** - a client (your phone, computer, Alexa, Echo, etc.) hears a distinct sound that our system thinks is important. The sound gets sent to our ever-expanding database, gets parsed, and triggers a message to notify you. All your devices get a popup: you phone's LED lights up, your smart bulbs blink, and a notification is sent to each device: *There's someone at your door!* Suddenly, a sense you may take for granted everyday becomes accessible to an entire group of people who don't.

## Introduction

Listen Hear is an app meant to offer basic audio cues to people who are hard of hearing in visual form. Many salient noises: a ambulance siren, a car honking, a baby crying; are all used by people to provide additional information about an important situation that requires attention. Those who are hard of hearing do not have these cues in life, and must rely more heavily on other senses and cues in the world. Listen Hear attempts to bring the gap in providing some insight to these people about the cues they would be otherwise missing.

Tools already exist to process human language, but non-spoken audio is an untapped market for many applications. Listen Hear hopes to use peak amplitude sinusoidal mapping, in additional to more traditional machine learning techniques to accurately detect important tones and noises, and label them correctly. With this growing source of information, we can provide assistance to people who are hard of hearing, as well as tap into a new source of research and data mining.

## Inspiration

Many of us know someone who is hard of hearing, if not completely deaf. Though not as dibilitating as other ailments, being hard of hearing hinders a person's ability to do certain basic tasks that many of us take for granted. Listen Here can offer a chance for people who struggle to hear to be able to share in a sense that can be useful, if not life-saving.

## Getting Started

### Prerequisites

Audio fingerprinting and recognition is achieved by using dejavu Python library. Details about the underlying algorithm and the corresponding list of dependencies is available on the GitHub repository for dejavu. Install dejavu and all its dependent libararies to run the code given in this repository.

[Dejavu respository on GitHub](https://github.com/worldveil/dejavu)

The MySQL database for storing audio fingerprints was hosted in cloud on Amazon RDS servers. The configuration file containing details of AWS credentials, is used in main.py and also excluded from this repository. General format for that configuration.config file should be as follows.

```
[configuration]
aws_db_host = <link to database host>
aws_db_name = <name of the database>
aws_db_user = <database user name>
aws_db_pwd = <password for the database user>
```

Firebase Cloud Messaging (FCM) is used to send push notifications to subscribed devices. pyfcm, the Python client for FCM, is used to trigger push notifications. pyfcm can be installed with pip using the commands below:

```
pip install pyfcm
```
OR
```
pip install git+https://github.com/olucurious/PyFCM.git
```

### Installing

Python code given in main.py calculates the audio fingerprints and stores them in the MySQL database at Amazon RDS. Code in the same file also performs recordings of WINDOW_SIZE seconds(currently set to 3) via system microphone in separate threads. Each thread performs matching with the entries in the MySQL database and pushes successful match information to all the registered devices via Google Firebase Cloud Messaging service.

Excecute the Python code in main.py file with:

```
python main.py
```


## Built With

* Amazon Web Services - Mobile Hub and MySQL Server
* Android - XML/Java via Android Studio
* HTML/Javascript - Desktop notifications

## Authors

* Sumit Kumar
* Jonathan Ta
* Francesco Fanizza

## Acknowledgements

* Uncommonhacks 2018
