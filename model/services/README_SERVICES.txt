================================================================================
                    AUTHENTICATION & CREDENTIAL SERVICES
                          "The Proof-of-Concept"
================================================================================
Author: Mohamed Abucar
Location: com.authservice.domain.model.services

I. ARCHITECTURAL INTENT
-----------------------
This layer handles the complex validation logic required for User Registration, 
Login, and Multi-Factor Authentication (MFA). It serves as the bridge between 
raw incoming credentials and the secure Domain Aggregates.

Unlike traditional services that return 'true/false', these services issue 
specialized "Proof" objects. These proofs are the ONLY keys accepted by the 
downstream Aggregate Factories to birth Sessions and Tokens.

II. THE CREDENTIAL VALIDATION PIPELINE
--------------------------------------
The system utilizes a "Validation-to-Proof" pipeline to ensure that no 
unauthorized state is ever created.



1. REGISTRATION: 
   - Validates uniqueness and password complexity.
   - Issues 'RegistrationProof' -> Used by UserCreationFactory.

2. LOGIN:
   - Orchestrates credential verification.
   - Issues 'SuccessfulAuthProof' (The "Authorization Ticket").
   - Issues 'FailedAuthProof' with 'AuthFailureReason' for secure auditing.

III. MODULE BREAKDOWN
---------------------
├── credentialService/
│   ├── LoginValidationService: 
│   │   The "Gatekeeper" that verifies identities. It is the only service 
│   │   capable of minting a 'SuccessfulAuthProof'.
│   │
│   ├── RegistrationValidationService: 
│   │   Ensures domain invariants for new users (email uniqueness, etc.) 
│   │   are satisfied before a 'RegistrationProof' is issued.
│   │
│   └── CredentialValidationService: 
│       Internal utility for low-level hashing and crypto-verification.
│
├── MFAService: 
│   Handles the temporal challenge-response logic for TOTP/SMS/Email codes.

IV. PROOF HIERARCHY (The "Key" System)
--------------------------------------
To prevent "Hallucinated Logins," the Login system uses a specialized 
Proof hierarchy:

- AbstractUserProof: Base identity container.
- SuccessfulAuthProof: Contains the UserID and Timestamp. **MANDATORY** for SessionCreationRequirements.
- FailedAuthProof: Contains failure reasons (WRONG_PASSWORD, USER_NOT_FOUND). 
  Ensures the UI can respond without leaking sensitive security info.

V. EVOLUTIONARY NOTE
--------------------
Though this module was authored prior to the 'Triple-Lock Requirement' 
standard, the 'SuccessfulAuthProof' produced here is perfectly compatible 
with the 'SessionCreationRequirement' builder. It represents the transition 
from anemic logic to Proof-Oriented Security.

================================================================================
    "A boolean is a guess. A Proof is a certainty."
================================================================================