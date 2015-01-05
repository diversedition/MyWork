Eugene Diver

I also looked into using Jackson & Groovy.  I decided to use the libraries I did, since it was the quickest to implement, so it was at least good enough for a start.  It turned out to be fast enough too.


This took about three hours.  I spent more time setting up the things I usually do in Unix, in a Windows environment.

#1.
a.Unzip the OTG.zip file into a project <home> directory.
b.Change directory to <home>/OTG
b.Edit the buid.xml ‘home’ variable to match <home>
c.Build the code : “<$ANT_HOME>/ant –d create_run_jar” by supplying the          
        correct setting for $ANT_HOME.
d.Run the jar file (output files will go to the <home>/OTG/data dir)
       java –jar otgTest.jar <dataFile name>
       example:   java –jar otgTest.jar otg-datasample
e.Note: A '.project' file exists for usage in Eclipse.


This took about two hours.

#2.  
a. Change directory to the <home>/OTG directory from step 1.
b. Run the ./bin/getDataTypesAndTotalWeight.sh script in a linux emulator

For example I used gitbash:

Eugene@INSPIRON17 /c/MyWork/OTG/bin
$ ./getDataTypesAndTotalWeight.sh
Data Types
 call : 2
 delivery : 1
 sending : 1
Total Weight:2.55
