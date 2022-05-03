## Use Case
### Which type of devices are out there?
1. embedded devices (OS is not accessible as user)
    1. e.g. io-key
2. non embedded devices (OS is accessible as user)
    1. e.g. thin edge, thick edge, iphone, android
3. Containerized environments, where the user is only able to upload a docker container 
    1. (gateway from some manufaturer.. e.g. Customer fronius)

### 

1. Didier Workflow  (Issue Certificate from the device 2 CA)
    1. Assumes you have access to the devices
    2. Assumes the devices has an operationg system which can execute such commands
    3. will not work for embedded Devices

### New Certificates can be 
    1. issued from a non embedded device (single)
    2. issued from a cumulocity sub-tenant (single and bulk) with the c8y-UI
    3. issued from a cumulocity advanced tenant (single and bulk)) with the c8y-UI
    4. issued from a cumulocity master tenant (single and bulk)) with the c8y-UI
    5. issued from an embedded device (not possible) (single and bulk)
        1. only possible if a new firmware with the certificate is installed in conjunction with the c8y-UI
    6. issued from a containerized environments (single)
        1. Needs to be clarified where the certificate is stored. Inside or outside the container
### Source for new Certificates 
    1. CA is Online in the cloud
    2. CA is On-premise (optional)
        1. Only if the on-premise customer do not want to use online
    3. CA is Online on thick edge if necessary
    4. For thick edge which is offline certificates needs to be generated either on 1, 2 or 3

### Type of certificates
    1. Root certificate
        1. Certificate of the Authority
    2. Initialize device with factory certificate (needs to be the Management Tenant or the Advanced tenant)
        1. is used as fall back, if the device has to be moved from one cumulocity tenant to another
        2. this is step 1 within an enrollment process
    3. Initialize Integrator Certificate are needed to support for manufacturer�integrator�operator lifecycle
        1. is used as fall back, if the device has to be moved from one cumulocity tenant to another
        2. This would be step 1a within an enrollment process (optional)
    4. Device  Intermediate certificate
        1. This would be step 2 within an enrollment process (optional)
    5. Device Certificate
        1. This is the certificate of the device of the end customer 
### Renewal of certificate
### Revoke of certificate
    1. A C8y user should be used to revoke a certificate from Sub Tenant
    2. A C8y user should be used to revoke a certificate from Advanced Tenant
    3. A C8y user should be used to revoke a certificate from Management Tenant
    4. Should a certificate be revoked from a device?
    5. Should a certificate be revoked from Nexus?
    6. 

### Deletion of certificate
    1. Is the deletion of a certificate necessary?
        1. if the diskspace (RAM, SSD, HDD) of the device is not enough

### Status of certificate
    1. Do we need to check the status of an certificate 
        1. from the device?
        2. from the server(CA)?
    2. Yes
        1. How will this be done for devices within a DMZ
        2. How will this be done for offline devices
        3. Which port is used for the status check of an device which possibly needs 2 be forwarded
        4. can OCSP be used of the device
        5. use SCVP 
    3. No
#### Online Certificate Status Protocol (OCSP)
    1. The Online Certificate Status Protocol (OCSP) is an Internet protocol used for obtaining the revocation status of an certificate.
    2. OCSP  only provides information on the revocation status of certificates, but does not check the mathematical correctness of the signatures of the certificates, their validity period or any usage restrictions specified in the certificate. In addition, the client must first determine a certification chain.
    3. To get the certificate chain the client do not have enough ressource

#### Server-based Certificate Validation Protocol (SCVP) https://www.rfc-editor.org/info/rfc5055
    1. The Server-based Certificate Validation Protocol (SCVP) is an Internet protocol for determining the path between an X.509 digital certificate and a trusted root (Delegated Path Discovery) and the validation of that path (Delegated Path Validation) according to a particular validation policy. 
    2. It needs to be clarified if Nexus supports this
    3. 

    