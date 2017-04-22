# Whatsapp Spammer
Whatsapp Spammer is a quick and dirty Java application to send multiple messages through the Whatsapp Web client.

## How to use
To use Whatsapp Spammer you need to run [Whatsapp Web](https://web.whatsapp.com) on your computer, then open Whatsapp Spammer, choose the source of the messages (single message or list of messages), fill in the fields and click run.  
You will be prompted with further instructions: **do not close the popup until you have followed the instructions**.
You will need to open Whatsapp Web and maximize it (so it occupies the whole screen), then you have to open the contact or group you want to send the messages to and click on the message box; finally you can click on "Ok" on the popup and the program will send the messages.  
**Do not click anything, move any window or open any program while the program is still sending messages.** You will be notified as the program will have sent all the messages.

## WSL (Whatsapp Spammer List)
One of the options offered by this application is to send a list of messages contained in a text file. The text file can have any extension, but it's advised to use one of these extensions: ```.txt .lst .wsl```  

### Format
The file must contain one message per line: if you want a message to be composed of multiple lines insert ```#NL``` where you want to break the line (e.g. ```First line#NL#Second line```).

### Wait directives
Lines starting with ```#wait``` are interpreted by the application as commands instead of text messages. These lines are called _wait directives_ as they tell the application how much time to wait after sending a message before sending the next one.  
Here is a simple example of a .WBL file with wait directives:  
```
First message
Second message
#wait 1000
Third message
Fourth message
#wait 2000
Fifth message
```

When these messages will be sent this will happen:
-   no initial wait directive: the program will use the default wait time (100ms);
-   send the first message
-   wait 100ms
-   send the second message
-   wait 100ms
-   wait directive: change wait time to 1000ms
-   send the third message
-   wait 1000ms
-   send the fourth message
-   wait 1000ms
-   wait directive: change wait time to 2000ms
-   send the fifth message
-   wait 2000ms

Note how each directive will change the wait time **for all the following messages**, not just for the next one.

## Disclaimer
This application could cause unwanted behaviours (including, but not limited to freezes, performance issues, forced restarts and crashes) on the device running the application and Whatsapp Web, on the device mobile running Whatsapp and the device(s) receiving the messages.  
The author of this software is not to be held responsible for any damage or problems caused by the use of this software.

## License
This application is licensed under the [MIT License](https://github.com/GioBonvi/WhatsappSpammer/blob/master/LICENSE).
