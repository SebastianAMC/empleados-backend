package com.gestion.empleados.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.empleados.exceptions.ResourceNotFoundException;
import com.gestion.empleados.model.Empleado;
import com.gestion.empleados.repository.IEmpleadoRepositorio;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins="http://localhost:4200")
public class EmpleadoControlador {

	@Autowired
	private IEmpleadoRepositorio repositorio;
	
	@GetMapping("/empleados")
	public List<Empleado> ListarTodosLosEmpleados(){
		return repositorio.findAll();
	}
	
	@PostMapping("/empleados")
	public Empleado guardarEmpleado(@RequestBody Empleado empleado) {
		return repositorio.save(empleado);
	}
	
	@GetMapping("/empleados/{id}")
	public ResponseEntity<Empleado> getEmpleadoById(@PathVariable Long id){	
		Empleado empleado = repositorio.findById(id)
							.orElseThrow(()->new ResourceNotFoundException
									("No existe el empleado con el ID: " + id));
		return ResponseEntity.ok(empleado);
	}
	
	@PutMapping("/empleados/{id}")
	public ResponseEntity<Empleado> updateEmpleado(@PathVariable Long id, @RequestBody Empleado detallesEmpleado){	
		Empleado empleado = repositorio.findById(id)
							.orElseThrow(()->new ResourceNotFoundException
									("No existe el empleado con el ID: " + id));
			empleado.setNombre(detallesEmpleado.getNombre());
			empleado.setApellido(detallesEmpleado.getApellido());
			empleado.setEmail(detallesEmpleado.getEmail());
			Empleado updatedEmpleado = repositorio.save(empleado);
		return ResponseEntity.ok(updatedEmpleado);
	} 
	
	@DeleteMapping("/empleados/{id}")
	public ResponseEntity<Map<String,Boolean>> eliminarEmpleado(@PathVariable Long id){
		Empleado empleado = repositorio.findById(id)
				.orElseThrow(()->new ResourceNotFoundException
						("No existe el empleado con el ID: " + id));
		repositorio.delete(empleado);
		Map<String, Boolean> respuesta = new HashMap<>();
		respuesta.put("eliminar", Boolean.TRUE);
		return ResponseEntity.ok(respuesta);
	} 
	
}
