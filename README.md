# Nexties-HealthBuddy-Agent --  Empowering Smart Health Triage with Agentic AI

## 📌 Project Description

**Nexties-HealthBuddy-Agent** is an intelligent, agentic healthcare assistant designed to help users assess their symptoms, receive triage-based guidance, and get connected to appropriate medical services—all with empathy, accuracy, and speed. This solution leverages the power of **Java SpringBoot**, **LangChain4j**, **Azure OpenAI**, **Azure Cognitive Search (RAG)**, and **Azure Maps** to deliver a privacy-aware, real-time digital health assistant that can scale across use cases.

---

## 🚀 Unique Selling Propositions (USP)

- 🤖 **Agentic Architecture with Modular Tools** – Executes real-time decisions using PatientTool, SymptomTool, DoctorTool, TeleconsultationTool, and EmergencyServiceTool.
- 🧠 **RAG-based Disease Understanding** – Uses retrieval-augmented generation to map symptoms to possible diseases from custom data.
- 🗺️ **Location-Aware Emergency Response** – Locates nearest hospitals using Azure Maps for emergency classification.
- 🏥 **Smart Symptom Triage** – Categorizes into BASIC, MODERATE, or CRITICAL cases with different actions: suggest homecare, book doctor, or alert emergency services.
- 📡 **WebSocket Real-Time Communication** – Ensures smooth bi-directional user-agent conversations.
- 🔒 **Privacy-Conscious Design** – Aligns with HIPAA-like principles for handling patient records.
- ⚙️ **Scalable & Extendable** – Multi-tenant design allows onboarding multiple hospitals and providers.

---

## ⚙️ AI Agent Workflow

1. **Greet & Comfort** – Agent welcomes the user and builds trust.
2. **Collect Patient Info** – Name, age, location, etc., then creates/updates patient record (via `PatientTool`).
3. **Symptom Collection** – Gathers detailed symptoms, timelines, and clarifies ambiguities.
4. **Disease Understanding (via RAG)** – Uses symptom-to-disease mapping from custom data with embeddings.
5. **Triage Classification** – Calls `SymptomsTriage` to classify as:
   - **BASIC** – Self-care with optional teleconsultation.
   - **MODERATE** – Medical visit/consultation needed.
   - **CRITICAL** – Emergency alert with ambulance + hospital booking.
6. **Execute Actions** – Based on category:
   - Book teleconsultation (`TeleconsultationTool`)
   - Schedule doctor visit (`DoctorTool`)
   - Trigger emergency protocol (`EmergencyServiceTool` + Azure Maps)
7. **Summarize Case** – Agent provides a complete summary and next steps to the patient.

---

## 🏗️ System Architecture

```plaintext
[User (Client App)]
        ↓ (API + WebSocket)
[Java SpringBoot Backend with LangChain4j]
        ↓
[LangChain Agent + Tools]
        ↓
[Azure AI Services]
    ├─ OpenAI (LLM)
    ├─ LangChain InMemory (RAG)
    └─ Tool Interfaces (PatientTool, DoctorTool, etc.)


