# Nexties-HealthBuddy-Agent --  Empowering Smart Health Triage with Agentic AI

## 📌 Project Description

**Nexties-HealthBuddy-Agent** is an intelligent, agentic healthcare assistant designed to help users assess their symptoms, receive triage-based guidance, and get connected to appropriate medical services—all with empathy, accuracy, and speed. This solution leverages the power of **Java SpringBoot**, **LangChain4j**, and **Azure OpenAI** to deliver a privacy-aware, real-time digital health assistant that can scale across use cases.

---

## 🚀 Unique Selling Propositions (USP)

- 🤖 **Agentic Architecture with Modular Tools** – Executes real-time decisions using PatientTool, SymptomTool, DoctorTool, TeleconsultationTool, and EmergencyServiceTool.
- 🧠 **RAG-based Disease Understanding** – Uses retrieval-augmented generation to map symptoms to possible diseases from custom data.
- 🗺️ **Location-Aware Emergency Response** – Locates nearest hospitals for emergency classification.
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
   - Trigger emergency protocol (`EmergencyServiceTool`)
7. **Summarize Case** – Agent provides a complete summary and next steps to the patient.

---
![image](https://github.com/user-attachments/assets/f4cf70f4-6a55-46d8-bf80-5ccd6c63e8c4)

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


**Project Structure**:
/backend

 ├── agent/
 │   ├── HealthAgent.java
 │   └── tools/
 │       ├── PatientTool.java
 │       ├── DoctorTool.java
 │       ├── SymptomTool.java
 │       ├── EmergencyServiceTool.java
 │       └── TeleconsultationTool.java
 ├── config/
 │   ├── LangChainConfig.java
 │   └── AzureMapConfig.java
 ├── service/
 │   └── PatientService.java
 ├── web/
 │   └── WebSocketHandler.java


 ## 🧰 Technology Stack

| Technology         | Purpose                                                                 |
|--------------------|-------------------------------------------------------------------------|
| **Java 17**         | Backend application development.                                       |
| **Spring Boot**     | REST API framework for AI agent interaction.                           |
| **Gradle 3.5+**     | Build automation and dependency management.                            |
| **LangChain4j**     | Integrating LLM agents with structured tools in Java.                  |
| **Azure AI Service**| Provides LLM (GPT-4o) for reasoning and dialogue flow.                 |
| **JavaScript**      | Enables interactive frontend functionality.                            |
| **React**           | Frontend UI framework for chat and form interface.                     |
| **HTML/CSS**        | Structure and styling of the web interface.                            |
| **LangChain InMemory RAG** | Enables retrieval of medical data from embedded sources.        |

---

## 📁 Project Structure

```
Repository Root
├── backend/
│   └── myhealthbuddy/
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/                  → Java source code
│       │   │   └── resources/
│       │   │       └── application.properties  → App config
│       └── build.gradle                  → Build script
└── ui/                                   → React-based frontend
```

---

## 🚀 Execution Steps

### 🖥️ Backend Setup

1. **Get Azure OpenAI credentials** from [https://ai.azure.com/](https://ai.azure.com/)
   - `AZURE_OPENAI_API_KEY`
   - `AZURE_OPENAI_ENDPOINT`
   - Model name (e.g., `gpt-4o`)

2. **Update Environment Config**
   Set in `application.properties` or environment variables:
   ```
   AZURE_OPENAI_API_KEY=<your-key>
   AZURE_OPENAI_ENDPOINT=<your-endpoint>
   LANGCHAIN_MODEL_NAME=gpt-4o
   ```

3. **Navigate to Backend Directory**
   ```bash
   cd backend/myhealthbuddy
   ```

4. **Run Application**
   Using terminal:
   ```bash
   ./gradlew build -x test     # Build the project without running tests
   ./gradlew bootRun           # Start backend server
   ```
   Or run directly from IntelliJ or VS Code.

   > Server runs at `http://localhost:8100`

---

### 🌐 Frontend Setup

1. **Ensure Node.js and npm are installed**

2. **Navigate to UI Directory**
   ```bash
   cd ui/
   ```

3. **Install Dependencies**
   ```bash
   npm install
   ```

4. **Run the App**
   ```bash
   npm run build   # Optional, for production build
   npm run dev     # Starts the development server
   ```

   > UI accessible at `http://localhost:5173`

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


