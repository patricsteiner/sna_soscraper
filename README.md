# Stackoverflow web scraper for social network analysis

## Setup
- checkout the project
- make sure application.properties has correct database configuration
- build and run project with gradle

## Usage
By default, a webpage is started with a basic GUI. Go to localhost:8080 to get an overview of the latest questions in the database. Please note, that the Operations that can be triggered in the GUI are quite computationally expensive, so be patient.

- click "scrape" to get some more questions
- use buttons to export nodes and edges --> creates a 1-mode network (tag-tag) by default! Change the controller accordingly if you want a 2-mode network (question-tag).
- import the files to Gephi and start analyzing stuff :)

## Notes
The Stackexchange API has limitations and restrictions, so scraping takes some time. Check the console output for more information.
