
# Blind Test

BlindTest is a multiplayer Quiz game developed only with Java 


## Tech Stack

**Database:** MySQL database (required to run this project)

**Client:** Java, JFrame

**Server:** Java


## Features

- Create account
- Sign In
- Sign Out
- Start Session
    * Pick a name for the session
    * Choose themes to specify the type of people you'll be quized about
    * Choose the number of question in this session
    * Wait for other users to start the game or start directly!
- Join existing session
    * Look in session table for online sessions to Join
    * You can see all the details about the session to help you pick :-)


## Run Locally

Create a mySQL database locally and run the blind_test.sql script in a mysql server to create the tables

Clone the project

```bash
  git@github.com:ivan-fr/BlindTest.git
```

Go to the project source directory

```bash
  cd src
```

Go to sockets directory

```bash
  cd sockets
```

Start ServerMain

```bash
  java ServerMain
```

Start multiple instances of ClienMain. Each instance represents a client.

```bash
  java ClientMain
```

