# tdtable

You can view multiple thread dumps in a table layout.

![](https://github.com/be-hase/tdtable/assets/903482/2cc02f79-6231-4326-931d-9c8909345954)

## Usage

```bash
jcmd PID Thread.print > hoge-1.txt
jcmd PID Thread.print > hoge-2.txt
jcmd PID Thread.print > hoge-3.txt

tdtable hoge-*.txt -o out.html
open out.html
```

## Install

### [Download the latest binary](https://github.com/be-hase/tdtable/releases/latest)

Use wget to download, gzipped pre-compiled binaries:

For instance, `BINARY=tdtable_macos_arm64`:

#### Compressed via tar.gz

```bash
wget "https://github.com/be-hase/tdtable/releases/latest/download/$BINARY.tar.gz" -O - | \
  tar xz && mv "$BINARY" /path/to/tdtable
```

#### Plain binary

```bash
wget "https://github.com/be-hase/tdtable/releases/latest/download/$BINARY" -O /path/to/tdtable && \
  chmod +x /path/to/tdtable
```
