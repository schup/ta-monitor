# CMI Monitor

The program polls a CMI interface of Technische Alternative every minute and publishes the values of inputs, outputs,
and
monitored values (digital and analog) to mqtt.

The only configuration required is the address and credentials for the CMI and the mqtt broker.

To get the names of the in/out pins, the CMI web interface is used (web scraping). For the values the JSON API is used.

https://www.ta.co.at/download/datei/17511763-cmi-json-api/

## Supported Devices

Currently only Urv16x2 is supported.

## Building

The tests currently required an active CMI instance.
Run gradle with '-x test' to exclude the unit test urn.

```shell
./gradlew build -x test
```

