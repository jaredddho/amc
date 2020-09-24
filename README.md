# Adobe Marketing Cloud
This multi-services spring boot project implements 
the integration of the products in Adobe Marketing Cloud 
using their respective API endpoints.

Project Modules:
- Core (core)
    - Contains the common codebase shared across the products.
    - Integration with Adobe.IO required for authentication.
    - Refresh of access tokens
- _Adobe Analytics (analytics)_
    - _Soon to be covered_
- Adobe Campaign Standard (campaign)
    - https://docs.adobe.com/content/help/en/campaign-standard/using/working-with-apis/get-started-apis.html
    - CRUD Profiles
    - Triggering of Transactional Messages
- Adobe Target (target)

Services:
- Core - Port 4602
- Campaign - Port 4603
- Target - Port 4604

Requisities:
- Generation of public and private keys from Adobe.IO
- application.properties
- application-template.properties with the required credentials
