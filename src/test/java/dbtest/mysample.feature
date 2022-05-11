# mysample.feature
Feature: Karate MySample
  Background:
    * url baseUrl2
    * def dbUtils = Java.type('dbtest.DbUtils')

  Scenario: 通常パターン_GET
    Given path '/stubrest'
    And param arg = 'foo'
    When method get

    Then status 200
    And match response == '#object'
    And match response.retid == '1001'
    * def ret = dbUtils.saveToJson('./build/test1.json', 'emp', "job = 'SALESMAN'")

  Scenario: 通常パターン_POST_JSON
    * def ret = dbUtils.saveToXml('./build/test1.before.xml', 'dept', null)

    Given path '/stubrest/1234/'
    And header Accept = 'application/json'
    And request {"id": "1234", "item": "23456789"}
    When method post

    Then status 200
    And match response == '#object'
    And match response.retid == '1001'
    And match response.id == '1234'
    And match response.reg_dttm == '#regex 20\\d{2}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\+\\d{2}:\\d{2}'
    And match response.upd_dttm == '#regex 20\\d{2}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}'
    * def ret = dbUtils.saveToCsv('./build/test1.after.csv', 'emp', null)

  Scenario: 通常パターン_POST_JSON_NOT_FOUND
    * def ret = dbUtils.saveToYml('./build/test1.before.yml', 'emp', "job = 'CLERK'")
    Given path '/stubrest/9876/'
    And request {"id": "9234", "item": "23456789"}
    When method post

    Then status 404
    And match response == '#object'
    And match response.errcode == '1234'
    And match response.msg == 'id must not 9.'
    * def ret = dbUtils.saveToXls('./build/test1.after.xls', 'dept', null)
