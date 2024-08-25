# selenide-framework-example
An automation framework based off from selenide

## Encrypting Credentials
Follow the steps below to encrypt `src/test/resources/credentials` folder:
1. Zip the whole credentials folder to a `.tar` file
````
cd src/test/resources
tar --posix -cvf credentials.tar credentials
````
2. Encrypt the file through gpg command:
```
 gpg --yes --symmetric --cipher-algo AES256 --passphrase "<<passphrase>>" --batch --output credentials.tar.gpg credentials.tar
```
## Decrypting Credentials
From root, enter the following commands:
````
gpg --batch --passphrase "<<passphrase>>" -d -o src/test/resources/credentials.tar src/test/resources/credentials.tar.gpg
cd src/test/resources/
tar -xvf credentials.tar
chmod -R 755 credentials
````