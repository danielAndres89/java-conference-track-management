# java-conference-track-management
Solución al problema "CONFERENCE TRACK MANAGEMENT"

About The Problem: Conference Track Management
You are planning a big programming conference and have received many proposals which have passed the initial screen process but you're having trouble fitting them into the time constraints of the day -- there are so many possibilities! So you write a program to do it for you.

    The conference has multiple tracks each of which has a morning and afternoon session.
    Each session contains multiple talks.
    Morning sessions begin at 9am and must finish by 12 noon, for lunch.
    Afternoon sessions begin at 1pm and must finish in time for the networking event.
    The networking event can start no earlier than 4:00 and no later than 5:00.
    No talk title has numbers in it.
    All talk lengths are either in minutes (not hours) or lightning (5 minutes).
    Presenters will be very punctual; there needs to be no gap between sessions. 

 
Note: Depending on how you choose to complete this problem, your solution may give a different ordering or combination of talks into tracks. This is acceptable; you don't need to exactly duplicate the sample output given here.

Result (Output)
Test input:
Writing Fast Tests Against Enterprise Rails 60min
Overdoing it in Python 45min
Lua for the Masses 30min
Ruby Errors from Mismatched Gem Versions 45min
Common Ruby Errors 45min
Rails for Python Developers lightning
Communicating Over Distance 60min
Accounting-Driven Development 45min
Woah 30min
Sit Down and Write 30min
Pair Programming vs Noise 45min
Rails Magic 60min
Ruby on Rails: Why We Should Move On 60min
Clojure Ate Scala (on my project) 45min
Programming in the Boondocks of Seattle 30min
Ruby vs. Clojure for Back-End Development 30min
Ruby on Rails Legacy App Maintenance 60min
A World Without HackerNews 30min
User Interface CSS in Rails Apps 30min

 

Test Output:
Track 1:
Morning Session:
09:00AM Ruby on Rails Legacy App Maintenance 
10:00AM Ruby on Rails: Why We Should Move On 
11:00AM Rails Magic 
12:00PM Lunch
Evening Session:
01:00PM Communicating Over Distance 
02:00PM Writing Fast Tests Against Enterprise Rails 
03:00PM Clojure Ate Scala (on my project) 
03:45PM Pair Programming vs Noise 
04:30PM User Interface CSS in Rails Apps 
05:00PM Networking Event
Track 2:
Morning Session:
09:00AM Accounting-Driven Development 
09:45AM Common Ruby Errors 
10:30AM Ruby Errors from Mismatched Gem Versions 
11:15AM Overdoing it in Python 
12:00PM Lunch
Evening Session:
01:00PM A World Without HackerNews 
01:30PM Ruby vs. Clojure for Back-End Development 
02:00PM Programming in the Boondocks of Seattle 
02:30PM Sit Down and Write 
03:00PM Woah 
03:30PM Lua for the Masses 
04:00PM Rails for Python Developers lightning
04:05PM Networking Event

Más información: https://gallery.technet.microsoft.com/Conference-Track-fd55f878

