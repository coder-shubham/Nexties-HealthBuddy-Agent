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
```

## 🧠 Architecture Explanation

**Frontend**: Users interact through a simple interface (chat or form) that connects via API/WebSocket.

**Backend (Java SpringBoot + LangChain4j)**:
- Hosts LangChain agent and tool logic.
- Manages API endpoints and WebSocket for communication.

**LLM Core**:
- Uses Azure OpenAI to drive the conversation flow.
- Retrieves medical knowledge using embedded symptom-disease data via RAG.

**LangChain Tools**:
- `PatientTool`: Manages patient records.
- `SymptomTool`: Retrieves symptom-disease associations from embeddings.
- `DoctorTool`: Finds doctor availability and books appointments.
- `TeleconsultationTool`: Manages online consultation scheduling.
- `EmergencyServiceTool`: Alerts emergency services and integrates with Azure Maps.

**In-Memory Embeddings**:
- Used to reduce unnecessary LLM token usage.
- Keeps session-local symptom data contextually retrievable.


## 🎯 Target Audience

- 🧍‍♂️ **General Public** – All age groups for self-assessment and virtual triage.
- 🏥 **Hospitals & Clinics** – Use as front-desk automation for triage and booking.
- 🧑‍⚕️ **Telehealth Platforms** – Add AI-first assistant layer before doctor interaction.
- 🏢 **Corporates & HR Teams** – Deploy for employee health checks and remote diagnosis.
- 🚑 **Emergency Services** – Quick alert system for critical users based on symptom analysis.

## 💡 Usability

- 📱 **Device Agnostic** – Works on mobile, desktop, or integrated kiosk interfaces.
- 🌐 **Multi-Tenant Ready** – Supports onboarding of multiple hospitals or care providers.
- 🗣️ **Human-like Interaction** – Empathetic and non-technical responses driven by AI prompts.
- 📊 **Medical History Tracking** – Patient history is preserved and updated with every session.
- 🔒 **Secure & Compliant** – Designed with data privacy and medical ethics in mind.

## 🔭 Future Enhancements

- 🗣️ **Voice Assistant Integration** – Add speech-to-text and text-to-speech with Azure Speech Services.
- 🌍 **Multilingual Support** – Expand reach with native language triage interactions.
- 🔐 **EHR/FHIR Support** – Connect to real-world Electronic Health Record systems.
- 🧾 **Insurance Pre-authorization** – Allow automated validation and claim suggestion.
- 📊 **Health Monitoring Dashboard** – View trends in user symptoms and risk prediction.
- 🧬 **Predictive Diagnostics** – Use long-term trends to proactively detect high-risk individuals.
- 🤝 **Doctor AI Validation** – Let doctors validate and enhance AI recommendations.


