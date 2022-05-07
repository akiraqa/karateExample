# mysampledatafile.feature
Feature: Karate MySampleDataFile
  Background:
    * url baseUrl2
    * def dataSet = read('data/testFile1.json')

  Scenario Outline: データ駆動パターン_GET [<id>]
    Given path '/stubrest'
    And param arg = '<id>'
    When method get

    Then status 200
    And match response == '#object'
    And match response.retid == '1001'

    Examples:
      | dataSet |

  Scenario Outline: データ駆動パターン_POST_JSON [<id>]
    Given path '/stubrest/<id>/'
    And header Accept = 'application/json'
    And request {id: '<id>', item: '<item>'}
    When method post

    Then status 200
    And match response == '#object'
    And match response.retid == '1001'
    And match response.id == '<id>'
    And match response.item == '<item>'
    And match response.reg_dttm == '#regex 20\\d{2}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\+\\d{2}:\\d{2}'
    And match response.upd_dttm == '#regex 20\\d{2}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}'
    # jsonまるごと比較する場合は、全項目の定義が必要...
    And match response == {id: '#notnull', item: '#string', foobar: '#notpresent', retid: '#string', age: '#number', location: '#ignore', reg_dttm: '#present', upd_dttm: '#present'}

    Examples:
      | dataSet |

