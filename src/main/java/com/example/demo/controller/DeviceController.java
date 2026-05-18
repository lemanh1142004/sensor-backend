package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Device;
import com.example.demo.repository.DeviceRepository;

@RestController
@RequestMapping("/api/devices")
@CrossOrigin("*")
public class DeviceController {

    private final DeviceRepository repo;

    public DeviceController(DeviceRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Device> all() {
        return repo.findAll();
    }

    @PostMapping
    public Device add(@RequestBody Device d) {
        return repo.save(d);
    }

    @PutMapping("/{id}")
    public Device update(@PathVariable Long id, @RequestBody Device d) {
        d.setId(id);
        return repo.save(d);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}