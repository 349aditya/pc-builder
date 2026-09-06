package com.pcbuilder.catalog.bootstrap;

import com.pcbuilder.catalog.entity.*;
import com.pcbuilder.catalog.repository.ComponentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final ComponentRepository componentRepository;

    public DatabaseSeeder(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Only seed if the database catalog is currently empty
        if (componentRepository.count() == 0) {
            seedDatabase();
        }
    }

    private void seedDatabase() {

        Cpu cpu1 = new Cpu();
        cpu1.setName("AMD Ryzen 7 7800X3D");
        cpu1.setBrand("AMD");
        cpu1.setModel("Ryzen 7 7800X3D");
        cpu1.setPrice(new BigDecimal("389.99"));
        cpu1.setStockQuantity(25);
        cpu1.setImageUrl("https://example.com/images/7800x3d.png");
        cpu1.setSocketType("AM5");
        cpu1.setCoreCount(8);
        cpu1.setThreadCount(16);
        cpu1.setBaseClockGhz(new BigDecimal("4.20"));
        cpu1.setBoostClockGhz(new BigDecimal("5.00"));
        cpu1.setTdpWatts(120);
        cpu1.setHasIntegratedGraphics(true);

        Cpu cpu2 = new Cpu();
        cpu2.setName("Intel Core i5-14600K");
        cpu2.setBrand("Intel");
        cpu2.setModel("Core i5-14600K");
        cpu2.setPrice(new BigDecimal("299.00"));
        cpu2.setStockQuantity(18);
        cpu2.setImageUrl("https://example.com/images/14600k.png");
        cpu2.setSocketType("LGA1700");
        cpu2.setCoreCount(14);
        cpu2.setThreadCount(20);
        cpu2.setBaseClockGhz(new BigDecimal("3.50"));
        cpu2.setBoostClockGhz(new BigDecimal("5.30"));
        cpu2.setTdpWatts(125);
        cpu2.setHasIntegratedGraphics(true);

        Motherboard mobo1 = new Motherboard();
        mobo1.setName("ASUS ROG STRIX B650-A GAMING WIFI");
        mobo1.setBrand("ASUS");
        mobo1.setModel("ROG STRIX B650-A");
        mobo1.setPrice(new BigDecimal("219.99"));
        mobo1.setStockQuantity(12);
        mobo1.setImageUrl("https://example.com/images/strix-b650.png");
        mobo1.setSocketType("AM5");
        mobo1.setFormFactor("ATX");
        mobo1.setChipset("B650");
        mobo1.setRamType("DDR5");
        mobo1.setRamSlots(4);
        mobo1.setMaxRamCapacityGb(192);
        mobo1.setM2Slots(3);
        mobo1.setSataPorts(4);

        Motherboard mobo2 = new Motherboard();
        mobo2.setName("MSI MPG Z790 CARBON WIFI");
        mobo2.setBrand("MSI");
        mobo2.setModel("MPG Z790 CARBON");
        mobo2.setPrice(new BigDecimal("359.99"));
        mobo2.setStockQuantity(8);
        mobo2.setImageUrl("https://example.com/images/msi-z790.png");
        mobo2.setSocketType("LGA1700");
        mobo2.setFormFactor("ATX");
        mobo2.setChipset("Z790");
        mobo2.setRamType("DDR5");
        mobo2.setRamSlots(4);
        mobo2.setMaxRamCapacityGb(192);
        mobo2.setM2Slots(5);
        mobo2.setSataPorts(6);

        // --- 3. RAM ---
        Ram ram1 = new Ram();
        ram1.setName("G.Skill Trident Z5 Neo RGB 32GB (2x16GB)");
        ram1.setBrand("G.Skill");
        ram1.setModel("F5-6000J3038F16GX2-TZ5NR");
        ram1.setPrice(new BigDecimal("114.99"));
        ram1.setStockQuantity(30);
        ram1.setImageUrl("https://example.com/images/gskill-32gb.png");
        ram1.setRamType("DDR5");
        ram1.setCapacityGb(32);
        ram1.setStickCount(2);
        ram1.setSpeedMhz(6000);
        ram1.setCasLatency(30);

        Gpu gpu1 = new Gpu();
        gpu1.setName("ASUS TUF Gaming GeForce RTX 4070 SUPER");
        gpu1.setBrand("ASUS");
        gpu1.setModel("TUF-RTX4070S-O12G");
        gpu1.setPrice(new BigDecimal("649.99"));
        gpu1.setStockQuantity(10);
        gpu1.setImageUrl("https://example.com/images/tuf-4070s.png");
        gpu1.setChipset("GeForce RTX 4070 SUPER");
        gpu1.setVramGb(12);
        gpu1.setLengthMm(301);
        gpu1.setTdpWatts(220);
        gpu1.setRecommendedPsuWattage(650);

        PcCase case1 = new PcCase();
        case1.setName("NZXT H6 Flow ATX Mid Tower");
        case1.setBrand("NZXT");
        case1.setModel("H6 Flow");
        case1.setPrice(new BigDecimal("109.99"));
        case1.setStockQuantity(15);
        case1.setImageUrl("https://example.com/images/h6-flow.png");
        case1.setMaxGpuLengthMm(365);
        case1.setMaxCpuCoolerHeightMm(163);
        case1.setMaxRadiatorSizeMm(360);
        case1.setSupportsAtx(true);
        case1.setSupportsMicroAtx(true);
        case1.setSupportsMiniItx(true);
        case1.setMax120mmFans(9);
        case1.setMax140mmFans(2);

        // --- 6. POWER SUPPLIES ---
        PowerSupply psu1 = new PowerSupply();
        psu1.setName("Corsair RM750e 750W 80+ Gold Modular");
        psu1.setBrand("Corsair");
        psu1.setModel("RM750e");
        psu1.setPrice(new BigDecimal("99.99"));
        psu1.setStockQuantity(20);
        psu1.setImageUrl("https://example.com/images/rm750e.png");
        psu1.setWattage(750);
        psu1.setEfficiencyRating("80+ Gold");
        psu1.setFormFactor("ATX");
        psu1.setIsModular(true);

        Storage ssd1 = new Storage();
        ssd1.setName("Samsung 990 Pro 2TB M.2 NVMe");
        ssd1.setBrand("Samsung");
        ssd1.setModel("MZ-V9P2T0B/AM");
        ssd1.setPrice(new BigDecimal("169.99"));
        ssd1.setStockQuantity(40);
        ssd1.setImageUrl("https://example.com/images/990pro-2tb.png");
        ssd1.setStorageType("SSD");
        ssd1.setFormFactor("M.2");
        ssd1.setInterfaceType("PCIe NVMe Gen 4");
        ssd1.setCapacityGb(2000);
        ssd1.setReadSpeedMbps(7450);
        ssd1.setWriteSpeedMbps(6900);

        CpuCooler cooler1 = new CpuCooler();
        cooler1.setName("Peerless Assassin 120 SE Air Cooler");
        cooler1.setBrand("Thermalright");
        cooler1.setModel("PA120 SE");
        cooler1.setPrice(new BigDecimal("35.90"));
        cooler1.setStockQuantity(50);
        cooler1.setImageUrl("https://example.com/images/pa120.png");
        cooler1.setCoolerType("AIR");
        cooler1.setHeightMm(155);
        cooler1.setRadiatorSizeMm(null);
        cooler1.setFanSizeMm(120);
        cooler1.setIsRgb(false);
        cooler1.setSupportedSockets(Set.of("AM4", "AM5", "LGA1700", "LGA1200"));

        CpuCooler cooler2 = new CpuCooler();
        cooler2.setName("Corsair iCUE H150i Elite Capellix XT Liquid Cooler");
        cooler2.setBrand("Corsair");
        cooler2.setModel("H150i EC XT");
        cooler2.setPrice(new BigDecimal("219.99"));
        cooler2.setStockQuantity(10);
        cooler2.setImageUrl("https://example.com/images/h150i.png");
        cooler2.setCoolerType("LIQUID");
        cooler2.setHeightMm(null);
        cooler2.setRadiatorSizeMm(360);
        cooler2.setFanSizeMm(120);
        cooler2.setIsRgb(true);
        cooler2.setSupportedSockets(Set.of("AM4", "AM5", "LGA1700", "LGA1200", "LGA2066"));

        CaseFan fan1 = new CaseFan();
        fan1.setName("Noctua NF-A12x25 PWM 120mm");
        fan1.setBrand("Noctua");
        fan1.setModel("NF-A12x25 PWM");
        fan1.setPrice(new BigDecimal("32.90"));
        fan1.setStockQuantity(60);
        fan1.setImageUrl("https://example.com/images/nf-a12.png");
        fan1.setFanSizeMm(120);
        fan1.setAirflowCfm(60.0);
        fan1.setNoiseDb(22.6);
        fan1.setIsRgb(false);

        componentRepository.saveAll(List.of(
                cpu1, cpu2,
                mobo1, mobo2,
                ram1,
                gpu1,
                case1,
                psu1,
                ssd1,
                cooler1, cooler2,
                fan1
        ));
    }
}
