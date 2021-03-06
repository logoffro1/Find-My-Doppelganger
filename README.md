# Find My Doppelganger
**This project was intended as a fun experiment**
## Description
Have you ever wondered if you a have a doppelganger? A look alike?

This project is trying to help you find your **fake** look alike.
## How it works
[thispersondoesnotexist](https://thispersondoesnotexist.com/) is a website which uses AI to generate real looking human faces. The possibilities are endless.

It is just a matter of time until it will generate a face that looks just like you.

By providing a photo of your face (a template), the program is going to search for a face that matches best the provided template.

It is not guaranteed that it will find a perfect match, actually the chances are very slim, however, given enough time it will find something pretty close, or at least it will give you a few laughs about the matches it found.

## Libraries used
[jsoup 1.13.1](https://jsoup.org/download)

[opencv 4.5.1](https://opencv.org/releases/)
## How to use it
Once you installed the libraries, you need to put into the _ImageInput_ folder a **front faced**, **clear**, and **up close** picture of your face. You can also put multiple pictures for better results. An example of how the picture should be is below: 

<img src="https://thispersondoesnotexist.com/image" width="300" height="300">

Whenever a match is found, the image will be placed in the _PossibleMatches_ folder.

***
#### Be aware that finding a suitable match might take time, a lot of time. That if it even finds a match.

