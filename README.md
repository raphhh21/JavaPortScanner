# Java Port Scanner

Simple port scanner in Java.

## Usage

`$ java -jar JavaPortScanner.jar <IPv4> [PortRange]`

- `<IPv4>` is a required parameter that is a valid IPv4 address
- `[PortRange]` is an optional parameter
  - If this is empty, scanner will try to scan all possible ports (can take a long time)
  - `PortStart-PortEnd` will scan from `PortStart` to `PortEnd`

## Examples

### Scanning all possible ports

```bash
$ java -jar JavaPortScanner.jar 127.0.0.1
Scanning 127.0.0.1:1-65535
2/65535 were open
80:     open
443:    open

```

### Scanning certain port(s) only

```bash
$ java -jar JavaPortScanner.jar 127.0.0.1 80
Scanning 127.0.0.1:80
1/1 were open
80:     open

$ java -jar JavaPortScanner.jar 127.0.0.1 80-81
Scanning 127.0.0.1:80-81
1/2 were open
80:     open

```
