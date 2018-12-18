# Stackoverflow web scraper for social network analysis

## Setup
- checkout the project
- make sure application.properties has correct database configuration
- build and run project with gradle

## GUI
note that the frontend is just for testing, it has blocking operations and no error handling, but if you still want to use it, go ahead:
- go to localhost:8080 to get an overwiev of the latests questions in the db
- click "scrape" to get some more
- use buttons to export nodes and edges --> creates a 1-mode network (tag-tag) by default! Change the controller if you want a 2-mode network (question-tag).
- import the files to gephi and start analyzing stuff :)
