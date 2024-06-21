document.addEventListener('DOMContentLoaded', function() {
    // URL base para las peticiones al backend
    const baseUrl = 'http://localhost:8080/api';

    // Función para cargar pacientes
    function cargarPacientes() {
        fetch(`${baseUrl}/pacientes`)
            .then(response => response.json())
            .then(data => {
                const tbody = document.getElementById('body-pacientes');
                tbody.innerHTML = '';
                data.forEach(paciente => {
                    const tr = document.createElement('tr');
                    tr.innerHTML = `
                        <td>${paciente.nombre}</td>
                        <td>${paciente.apellido}</td>
                        <td>${paciente.dni}</td>
                        <td>${paciente.fechaIngreso}</td>
                        <td>
                            <button onclick="eliminarPaciente(${paciente.id})">Eliminar</button>
                        </td>
                    `;
                    tbody.appendChild(tr);
                });
            });
    }

    // Función para cargar odontólogos
    function cargarOdontologos() {
        fetch(`${baseUrl}/odontologos`)
            .then(response => response.json())
            .then(data => {
                const select = document.getElementById('odontologoId');
                select.innerHTML = '';
                data.forEach(odontologo => {
                    const option = document.createElement('option');
                    option.value = odontologo.id;
                    option.textContent = `${odontologo.nombre} ${odontologo.apellido}`;
                    select.appendChild(option);
                });
            });
    }

    // Función para cargar pacientes en select de turno
    function cargarPacientesParaTurno() {
        fetch(`${baseUrl}/pacientes`)
            .then(response => response.json())
            .then(data => {
                const select = document.getElementById('pacienteId');
                select.innerHTML = '';
                data.forEach(paciente => {
                    const option = document.createElement('option');
                    option.value = paciente.id;
                    option.textContent = `${paciente.nombre} ${paciente.apellido}`;
                    select.appendChild(option);
                });
            });
    }

    // Event listener para guardar paciente
    document.getElementById('form-paciente').addEventListener('submit', function(event) {
        event.preventDefault();
        const formData = new FormData(event.target);
        const paciente = {
            nombre: formData.get('nombre'),
            apellido: formData.get('apellido'),
            dni: formData.get('dni'),
            fechaIngreso: formData.get('fechaIngreso')
        };

        fetch(`${baseUrl}/pacientes`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(paciente)
        })
        .then(response => response.json())
        .then(data => {
            cargarPacientes();
            event.target.reset();
        });
    });

    // Inicializar la carga de datos
    cargarPacientes();
    cargarOdontologos();
    cargarPacientesParaTurno();
});
