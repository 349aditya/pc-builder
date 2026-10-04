-- PC Builder catalog seed v4: Exclusively In-Stock Vishal Peripherals Catalog
-- Updated with verified live in-stock pricing from vishalperipherals.com
USE pcbuilder_catalog;
START TRANSACTION;

DELIMITER //
DROP PROCEDURE IF EXISTS upsert_component_base //
CREATE PROCEDURE upsert_component_base(
    IN p_category VARCHAR(32),
    IN p_name VARCHAR(255),
    IN p_brand VARCHAR(100),
    IN p_model VARCHAR(150),
    IN p_price DECIMAL(10,2),
    IN p_stock INT,
    OUT p_id BIGINT
)
BEGIN
    SET p_id = NULL;
    SELECT MAX(id) INTO p_id 
    FROM components 
    WHERE category_type = p_category AND model = p_model;
    
    IF p_id IS NULL THEN
        INSERT INTO components (category_type, name, brand, model, price, stock_quantity, image_url, created_at, updated_at)
        VALUES (p_category, p_name, p_brand, p_model, p_price, p_stock, NULL, NOW(), NOW());
        SET p_id = LAST_INSERT_ID();
    ELSE
        UPDATE components 
        SET name = p_name, brand = p_brand, price = p_price, stock_quantity = p_stock, updated_at = NOW()
        WHERE id = p_id;
    END IF;
END //
DELIMITER ;

-- ============================================================================
-- 1. PROCESSORS (CPUs) - Vishal Peripherals In-Stock
-- ============================================================================

-- AM4 Platform
CALL upsert_component_base('CPU','AMD Ryzen 3 3200G','AMD','YD3200C5FHBOX',5950.00,25,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM4',4,4,3.60,4.00,65,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='AM4',core_count=4,thread_count=4,base_clock_ghz=3.60,boost_clock_ghz=4.00,tdp_watts=65,has_integrated_graphics=TRUE;

CALL upsert_component_base('CPU','AMD Ryzen 5 3400G','AMD','YD3400C5FHBOX',7700.00,20,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM4',4,8,3.70,4.20,65,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='AM4',core_count=4,thread_count=8,base_clock_ghz=3.70,boost_clock_ghz=4.20,tdp_watts=65,has_integrated_graphics=TRUE;

CALL upsert_component_base('CPU','AMD Ryzen 5 5500','AMD','100-100000457BOX',8950.00,30,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM4',6,12,3.60,4.20,65,FALSE) 
ON DUPLICATE KEY UPDATE socket_type='AM4',core_count=6,thread_count=12,base_clock_ghz=3.60,boost_clock_ghz=4.20,tdp_watts=65,has_integrated_graphics=FALSE;

CALL upsert_component_base('CPU','AMD Ryzen 5 5600','AMD','100-100000927BOX',12700.00,35,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM4',6,12,3.50,4.40,65,FALSE) 
ON DUPLICATE KEY UPDATE socket_type='AM4',core_count=6,thread_count=12,base_clock_ghz=3.50,boost_clock_ghz=4.40,tdp_watts=65,has_integrated_graphics=FALSE;

CALL upsert_component_base('CPU','AMD Ryzen 5 5600X','AMD','100-100000065BOX',14950.00,25,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM4',6,12,3.70,4.60,65,FALSE) 
ON DUPLICATE KEY UPDATE socket_type='AM4',core_count=6,thread_count=12,base_clock_ghz=3.70,boost_clock_ghz=4.60,tdp_watts=65,has_integrated_graphics=FALSE;

CALL upsert_component_base('CPU','AMD Ryzen 5 5600GT','AMD','100-100001488BOX',16500.00,18,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM4',6,12,3.60,4.60,65,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='AM4',core_count=6,thread_count=12,base_clock_ghz=3.60,boost_clock_ghz=4.60,tdp_watts=65,has_integrated_graphics=TRUE;

CALL upsert_component_base('CPU','AMD Ryzen 7 5700X OEM','AMD','100-000000926',17100.00,20,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM4',8,16,3.40,4.60,65,FALSE) 
ON DUPLICATE KEY UPDATE socket_type='AM4',core_count=8,thread_count=16,base_clock_ghz=3.40,boost_clock_ghz=4.60,tdp_watts=65,has_integrated_graphics=FALSE;

CALL upsert_component_base('CPU','AMD Ryzen 7 5700G','AMD','100-100000263BOX',20900.00,15,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM4',8,16,3.80,4.60,65,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='AM4',core_count=8,thread_count=16,base_clock_ghz=3.80,boost_clock_ghz=4.60,tdp_watts=65,has_integrated_graphics=TRUE;

CALL upsert_component_base('CPU','AMD Ryzen 7 5800X3D','AMD','100-100000510WOF',37100.00,8,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM4',8,16,3.40,4.50,105,FALSE) 
ON DUPLICATE KEY UPDATE socket_type='AM4',core_count=8,thread_count=16,base_clock_ghz=3.40,boost_clock_ghz=4.50,tdp_watts=105,has_integrated_graphics=FALSE;

-- AM5 Platform
CALL upsert_component_base('CPU','AMD Ryzen 5 7500F','AMD','100-000001083',14950.00,30,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM5',6,12,3.70,5.00,65,FALSE) 
ON DUPLICATE KEY UPDATE socket_type='AM5',core_count=6,thread_count=12,base_clock_ghz=3.70,boost_clock_ghz=5.00,tdp_watts=65,has_integrated_graphics=FALSE;

CALL upsert_component_base('CPU','AMD Ryzen 5 8500G','AMD','100-100000931BOX',15100.00,20,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM5',6,12,3.50,5.00,65,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='AM5',core_count=6,thread_count=12,base_clock_ghz=3.50,boost_clock_ghz=5.00,tdp_watts=65,has_integrated_graphics=TRUE;

CALL upsert_component_base('CPU','AMD Ryzen 7 8700F','AMD','100-100001590BOX',20200.00,18,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM5',8,16,4.10,5.00,65,FALSE) 
ON DUPLICATE KEY UPDATE socket_type='AM5',core_count=8,thread_count=16,base_clock_ghz=4.10,boost_clock_ghz=5.00,tdp_watts=65,has_integrated_graphics=FALSE;

CALL upsert_component_base('CPU','AMD Ryzen 5 9600X','AMD','100-100001405WOF',20400.00,25,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM5',6,12,3.90,5.40,65,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='AM5',core_count=6,thread_count=12,base_clock_ghz=3.90,boost_clock_ghz=5.40,tdp_watts=65,has_integrated_graphics=TRUE;

CALL upsert_component_base('CPU','AMD Ryzen 5 7500X3D OEM','AMD','100-000001500',23950.00,12,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM5',6,12,4.00,5.00,95,FALSE) 
ON DUPLICATE KEY UPDATE socket_type='AM5',core_count=6,thread_count=12,base_clock_ghz=4.00,boost_clock_ghz=5.00,tdp_watts=95,has_integrated_graphics=FALSE;

CALL upsert_component_base('CPU','AMD Ryzen 7 7700X','AMD','100-100000591WOF',26500.00,18,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM5',8,16,4.50,5.40,105,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='AM5',core_count=8,thread_count=16,base_clock_ghz=4.50,boost_clock_ghz=5.40,tdp_watts=105,has_integrated_graphics=TRUE;

CALL upsert_component_base('CPU','AMD Ryzen 7 8700G','AMD','100-100001236BOX',28200.00,15,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM5',8,16,4.20,5.10,65,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='AM5',core_count=8,thread_count=16,base_clock_ghz=4.20,boost_clock_ghz=5.10,tdp_watts=65,has_integrated_graphics=TRUE;

CALL upsert_component_base('CPU','AMD Ryzen 7 9700X','AMD','100-100001404WOF',28700.00,20,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM5',8,16,3.80,5.50,65,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='AM5',core_count=8,thread_count=16,base_clock_ghz=3.80,boost_clock_ghz=5.50,tdp_watts=65,has_integrated_graphics=TRUE;

CALL upsert_component_base('CPU','AMD Ryzen 7 7800X3D OEM','AMD','100-000000910',31200.00,15,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM5',8,16,4.20,5.00,120,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='AM5',core_count=8,thread_count=16,base_clock_ghz=4.20,boost_clock_ghz=5.00,tdp_watts=120,has_integrated_graphics=TRUE;

CALL upsert_component_base('CPU','AMD Ryzen 9 7900X','AMD','100-100000589WOF',34400.00,14,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM5',12,24,4.70,5.60,170,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='AM5',core_count=12,thread_count=24,base_clock_ghz=4.70,boost_clock_ghz=5.60,tdp_watts=170,has_integrated_graphics=TRUE;

CALL upsert_component_base('CPU','AMD Ryzen 9 9900X','AMD','100-100000662WOF',41200.00,12,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM5',12,24,4.40,5.60,120,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='AM5',core_count=12,thread_count=24,base_clock_ghz=4.40,boost_clock_ghz=5.60,tdp_watts=120,has_integrated_graphics=TRUE;

CALL upsert_component_base('CPU','AMD Ryzen 7 9800X3D','AMD','100-100001084WOF',48200.00,10,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM5',8,16,4.70,5.20,120,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='AM5',core_count=8,thread_count=16,base_clock_ghz=4.70,boost_clock_ghz=5.20,tdp_watts=120,has_integrated_graphics=TRUE;

CALL upsert_component_base('CPU','AMD Ryzen 9 9950X OEM','AMD','100-000001277',50700.00,8,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM5',16,32,4.30,5.70,170,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='AM5',core_count=16,thread_count=32,base_clock_ghz=4.30,boost_clock_ghz=5.70,tdp_watts=170,has_integrated_graphics=TRUE;

CALL upsert_component_base('CPU','AMD Ryzen 9 9950X3D','AMD','100-100000719WOF',71200.00,6,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'AM5',16,32,4.30,5.70,170,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='AM5',core_count=16,thread_count=32,base_clock_ghz=4.30,boost_clock_ghz=5.70,tdp_watts=170,has_integrated_graphics=TRUE;

-- Intel Platform (LGA1200, LGA1700, LGA1851)
CALL upsert_component_base('CPU','Intel Core i3-10100F OEM','Intel','CM8070104291318',7300.00,20,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'LGA1200',4,8,3.60,4.30,65,FALSE) 
ON DUPLICATE KEY UPDATE socket_type='LGA1200',core_count=4,thread_count=8,base_clock_ghz=3.60,boost_clock_ghz=4.30,tdp_watts=65,has_integrated_graphics=FALSE;

CALL upsert_component_base('CPU','Intel Core i3-14100F','Intel','BX8071514100F',10700.00,22,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'LGA1700',4,8,3.50,4.70,58,FALSE) 
ON DUPLICATE KEY UPDATE socket_type='LGA1700',core_count=4,thread_count=8,base_clock_ghz=3.50,boost_clock_ghz=4.70,tdp_watts=58,has_integrated_graphics=FALSE;

CALL upsert_component_base('CPU','Intel Core Ultra 5 225F','Intel','BXC80768225F',13200.00,18,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'LGA1851',10,10,3.30,4.90,65,FALSE) 
ON DUPLICATE KEY UPDATE socket_type='LGA1851',core_count=10,thread_count=10,base_clock_ghz=3.30,boost_clock_ghz=4.90,tdp_watts=65,has_integrated_graphics=FALSE;

CALL upsert_component_base('CPU','Intel Core i5-12400F','Intel','BX8071512400F',14000.00,25,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'LGA1700',6,12,2.50,4.40,65,FALSE) 
ON DUPLICATE KEY UPDATE socket_type='LGA1700',core_count=6,thread_count=12,base_clock_ghz=2.50,boost_clock_ghz=4.40,tdp_watts=65,has_integrated_graphics=FALSE;

CALL upsert_component_base('CPU','Intel Core i5-14400F','Intel','BX8071514400F',16550.00,20,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'LGA1700',10,16,2.50,4.70,65,FALSE) 
ON DUPLICATE KEY UPDATE socket_type='LGA1700',core_count=10,thread_count=16,base_clock_ghz=2.50,boost_clock_ghz=4.70,tdp_watts=65,has_integrated_graphics=FALSE;

CALL upsert_component_base('CPU','Intel Core Ultra 7 270K Plus OEM','Intel','CM8076805128000',32990.00,10,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'LGA1851',24,24,3.70,5.50,125,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='LGA1851',core_count=24,thread_count=24,base_clock_ghz=3.70,boost_clock_ghz=5.50,tdp_watts=125,has_integrated_graphics=TRUE;

CALL upsert_component_base('CPU','Intel Core i9-14900K','Intel','BX8071514900K',46700.00,10,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'LGA1700',24,32,3.20,6.00,125,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='LGA1700',core_count=24,thread_count=32,base_clock_ghz=3.20,boost_clock_ghz=6.00,tdp_watts=125,has_integrated_graphics=TRUE;

CALL upsert_component_base('CPU','Intel Core Ultra 9 285K','Intel','BXC80768285K',60200.00,8,@id);
INSERT INTO cpus (component_id,socket_type,core_count,thread_count,base_clock_ghz,boost_clock_ghz,tdp_watts,has_integrated_graphics) 
VALUES (@id,'LGA1851',24,24,3.70,5.70,125,TRUE) 
ON DUPLICATE KEY UPDATE socket_type='LGA1851',core_count=24,thread_count=24,base_clock_ghz=3.70,boost_clock_ghz=5.70,tdp_watts=125,has_integrated_graphics=TRUE;

-- ============================================================================
-- 2. MOTHERBOARDS - Vishal Peripherals In-Stock (MSI)
-- ============================================================================

CALL upsert_component_base('MOTHERBOARD','MSI B550M-A PRO Micro ATX','MSI','B550M-A-PRO',6350.00,20,@id);
INSERT INTO motherboards (component_id,socket_type,form_factor,chipset,ram_type,ram_slots,max_ram_capacity_gb,m2_slots,sata_ports) 
VALUES (@id,'AM4','Micro-ATX','B550','DDR4',2,64,1,4) 
ON DUPLICATE KEY UPDATE socket_type='AM4',form_factor='Micro-ATX',chipset='B550',ram_type='DDR4',ram_slots=2,max_ram_capacity_gb=64,m2_slots=1,sata_ports=4;

CALL upsert_component_base('MOTHERBOARD','MSI PRO H610M-E DDR5','MSI','PRO-H610M-E-D5',6950.00,25,@id);
INSERT INTO motherboards (component_id,socket_type,form_factor,chipset,ram_type,ram_slots,max_ram_capacity_gb,m2_slots,sata_ports) 
VALUES (@id,'LGA1700','Micro-ATX','H610','DDR5',2,96,1,4) 
ON DUPLICATE KEY UPDATE socket_type='LGA1700',form_factor='Micro-ATX',chipset='H610',ram_type='DDR5',ram_slots=2,max_ram_capacity_gb=96,m2_slots=1,sata_ports=4;

CALL upsert_component_base('MOTHERBOARD','MSI PRO B650M-B Micro ATX','MSI','PRO-B650M-B',9150.00,18,@id);
INSERT INTO motherboards (component_id,socket_type,form_factor,chipset,ram_type,ram_slots,max_ram_capacity_gb,m2_slots,sata_ports) 
VALUES (@id,'AM5','Micro-ATX','B650','DDR5',2,96,1,4) 
ON DUPLICATE KEY UPDATE socket_type='AM5',form_factor='Micro-ATX',chipset='B650',ram_type='DDR5',ram_slots=2,max_ram_capacity_gb=96,m2_slots=1,sata_ports=4;

CALL upsert_component_base('MOTHERBOARD','MSI PRO B760M-E Micro ATX','MSI','PRO-B760M-E',9150.00,22,@id);
INSERT INTO motherboards (component_id,socket_type,form_factor,chipset,ram_type,ram_slots,max_ram_capacity_gb,m2_slots,sata_ports) 
VALUES (@id,'LGA1700','Micro-ATX','B760','DDR4',2,64,2,4) 
ON DUPLICATE KEY UPDATE socket_type='LGA1700',form_factor='Micro-ATX',chipset='B760',ram_type='DDR4',ram_slots=2,max_ram_capacity_gb=64,m2_slots=2,sata_ports=4;

CALL upsert_component_base('MOTHERBOARD','MSI B650M GAMING WIFI DDR5','MSI','B650M-GAMING-WIFI',10750.00,30,@id);
INSERT INTO motherboards (component_id,socket_type,form_factor,chipset,ram_type,ram_slots,max_ram_capacity_gb,m2_slots,sata_ports) 
VALUES (@id,'AM5','Micro-ATX','B650','DDR5',2,96,2,4) 
ON DUPLICATE KEY UPDATE socket_type='AM5',form_factor='Micro-ATX',chipset='B650',ram_type='DDR5',ram_slots=2,max_ram_capacity_gb=96,m2_slots=2,sata_ports=4;

CALL upsert_component_base('MOTHERBOARD','MSI B650M-P PRO AM5','MSI','B650M-P-PRO',11100.00,20,@id);
INSERT INTO motherboards (component_id,socket_type,form_factor,chipset,ram_type,ram_slots,max_ram_capacity_gb,m2_slots,sata_ports) 
VALUES (@id,'AM5','Micro-ATX','B650','DDR5',4,192,2,4) 
ON DUPLICATE KEY UPDATE socket_type='AM5',form_factor='Micro-ATX',chipset='B650',ram_type='DDR5',ram_slots=4,max_ram_capacity_gb=192,m2_slots=2,sata_ports=4;

CALL upsert_component_base('MOTHERBOARD','MSI B760M BOMBER WIFI DDR5','MSI','B760M-BOMBER-WIFI-D5',11550.00,18,@id);
INSERT INTO motherboards (component_id,socket_type,form_factor,chipset,ram_type,ram_slots,max_ram_capacity_gb,m2_slots,sata_ports) 
VALUES (@id,'LGA1700','Micro-ATX','B760','DDR5',2,96,2,4) 
ON DUPLICATE KEY UPDATE socket_type='LGA1700',form_factor='Micro-ATX',chipset='B760',ram_type='DDR5',ram_slots=2,max_ram_capacity_gb=96,m2_slots=2,sata_ports=4;

CALL upsert_component_base('MOTHERBOARD','MSI B760M-P PRO DDR5','MSI','B760M-P-PRO-D5',11950.00,16,@id);
INSERT INTO motherboards (component_id,socket_type,form_factor,chipset,ram_type,ram_slots,max_ram_capacity_gb,m2_slots,sata_ports) 
VALUES (@id,'LGA1700','Micro-ATX','B760','DDR5',4,192,2,4) 
ON DUPLICATE KEY UPDATE socket_type='LGA1700',form_factor='Micro-ATX',chipset='B760',ram_type='DDR5',ram_slots=4,max_ram_capacity_gb=192,m2_slots=2,sata_ports=4;

CALL upsert_component_base('MOTHERBOARD','MSI AMD B650-S PRO WIFI ATX','MSI','B650-S-PRO-WIFI',14550.00,15,@id);
INSERT INTO motherboards (component_id,socket_type,form_factor,chipset,ram_type,ram_slots,max_ram_capacity_gb,m2_slots,sata_ports) 
VALUES (@id,'AM5','ATX','B650','DDR5',4,192,2,4) 
ON DUPLICATE KEY UPDATE socket_type='AM5',form_factor='ATX',chipset='B650',ram_type='DDR5',ram_slots=4,max_ram_capacity_gb=192,m2_slots=2,sata_ports=4;

CALL upsert_component_base('MOTHERBOARD','MSI B650M-A PRO WIFI','MSI','B650M-A-PRO-WIFI',15550.00,14,@id);
INSERT INTO motherboards (component_id,socket_type,form_factor,chipset,ram_type,ram_slots,max_ram_capacity_gb,m2_slots,sata_ports) 
VALUES (@id,'AM5','Micro-ATX','B650','DDR5',4,192,2,4) 
ON DUPLICATE KEY UPDATE socket_type='AM5',form_factor='Micro-ATX',chipset='B650',ram_type='DDR5',ram_slots=4,max_ram_capacity_gb=192,m2_slots=2,sata_ports=4;

CALL upsert_component_base('MOTHERBOARD','MSI Z790 GAMING PLUS WIFI','MSI','Z790-GAMING-PLUS-WIFI',24050.00,12,@id);
INSERT INTO motherboards (component_id,socket_type,form_factor,chipset,ram_type,ram_slots,max_ram_capacity_gb,m2_slots,sata_ports) 
VALUES (@id,'LGA1700','ATX','Z790','DDR5',4,192,4,6) 
ON DUPLICATE KEY UPDATE socket_type='LGA1700',form_factor='ATX',chipset='Z790',ram_type='DDR5',ram_slots=4,max_ram_capacity_gb=192,m2_slots=4,sata_ports=6;

CALL upsert_component_base('MOTHERBOARD','MSI X870E GAMING PLUS WIFI ATX','MSI','X870E-GAMING-PLUS-WIFI',25250.00,10,@id);
INSERT INTO motherboards (component_id,socket_type,form_factor,chipset,ram_type,ram_slots,max_ram_capacity_gb,m2_slots,sata_ports) 
VALUES (@id,'AM5','ATX','X870E','DDR5',4,256,4,4) 
ON DUPLICATE KEY UPDATE socket_type='AM5',form_factor='ATX',chipset='X870E',ram_type='DDR5',ram_slots=4,max_ram_capacity_gb=256,m2_slots=4,sata_ports=4;

CALL upsert_component_base('MOTHERBOARD','MSI Z890 GAMING PLUS WIFI','MSI','Z890-GAMING-PLUS-WIFI',27050.00,8,@id);
INSERT INTO motherboards (component_id,socket_type,form_factor,chipset,ram_type,ram_slots,max_ram_capacity_gb,m2_slots,sata_ports) 
VALUES (@id,'LGA1851','ATX','Z890','DDR5',4,256,4,4) 
ON DUPLICATE KEY UPDATE socket_type='LGA1851',form_factor='ATX',chipset='Z890',ram_type='DDR5',ram_slots=4,max_ram_capacity_gb=256,m2_slots=4,sata_ports=4;

CALL upsert_component_base('MOTHERBOARD','MSI PRO Z890-A WIFI ATX','MSI','PRO-Z890-A-WIFI',28150.00,8,@id);
INSERT INTO motherboards (component_id,socket_type,form_factor,chipset,ram_type,ram_slots,max_ram_capacity_gb,m2_slots,sata_ports) 
VALUES (@id,'LGA1851','ATX','Z890','DDR5',4,256,4,4) 
ON DUPLICATE KEY UPDATE socket_type='LGA1851',form_factor='ATX',chipset='Z890',ram_type='DDR5',ram_slots=4,max_ram_capacity_gb=256,m2_slots=4,sata_ports=4;

CALL upsert_component_base('MOTHERBOARD','MSI MAG X870E GAMING PLUS MAX WIFI','MSI','MAG-X870E-GAMING-PLUS-MAX',28850.00,6,@id);
INSERT INTO motherboards (component_id,socket_type,form_factor,chipset,ram_type,ram_slots,max_ram_capacity_gb,m2_slots,sata_ports) 
VALUES (@id,'AM5','ATX','X870E','DDR5',4,256,4,4) 
ON DUPLICATE KEY UPDATE socket_type='AM5',form_factor='ATX',chipset='X870E',ram_type='DDR5',ram_slots=4,max_ram_capacity_gb=256,m2_slots=4,sata_ports=4;

CALL upsert_component_base('MOTHERBOARD','MSI MPG Z890 CARBON WIFI','MSI','MPG-Z890-CARBON-WIFI',53350.00,4,@id);
INSERT INTO motherboards (component_id,socket_type,form_factor,chipset,ram_type,ram_slots,max_ram_capacity_gb,m2_slots,sata_ports) 
VALUES (@id,'LGA1851','ATX','Z890','DDR5',4,256,5,4) 
ON DUPLICATE KEY UPDATE socket_type='LGA1851',form_factor='ATX',chipset='Z890',ram_type='DDR5',ram_slots=4,max_ram_capacity_gb=256,m2_slots=5,sata_ports=4;

-- ============================================================================
-- 3. RAM - Vishal Peripherals In-Stock
-- ============================================================================

CALL upsert_component_base('RAM','Corsair Vengeance LPX 8GB DDR4 3200MHz','Corsair','CMK8GX4M1E3200C16',2100.00,50,@id);
INSERT INTO ram (component_id,ram_type,capacity_gb,stick_count,speed_mhz,cas_latency) 
VALUES (@id,'DDR4',8,1,3200,16) 
ON DUPLICATE KEY UPDATE ram_type='DDR4',capacity_gb=8,stick_count=1,speed_mhz=3200,cas_latency=16;

CALL upsert_component_base('RAM','G.Skill Ripjaws V 16GB DDR4 3200MHz','G.Skill','F4-3200C16S-16GVR',3850.00,40,@id);
INSERT INTO ram (component_id,ram_type,capacity_gb,stick_count,speed_mhz,cas_latency) 
VALUES (@id,'DDR4',16,1,3200,16) 
ON DUPLICATE KEY UPDATE ram_type='DDR4',capacity_gb=16,stick_count=1,speed_mhz=3200,cas_latency=16;

CALL upsert_component_base('RAM','Corsair Vengeance LPX 32GB (2x16GB) DDR4 3200MHz','Corsair','CMK32GX4M2E3200C16',7400.00,30,@id);
INSERT INTO ram (component_id,ram_type,capacity_gb,stick_count,speed_mhz,cas_latency) 
VALUES (@id,'DDR4',32,2,3200,16) 
ON DUPLICATE KEY UPDATE ram_type='DDR4',capacity_gb=32,stick_count=2,speed_mhz=3200,cas_latency=16;

CALL upsert_component_base('RAM','Kingston ValueRAM 16GB DDR5 5600MHz','Kingston','KVR56U46BS8-16',28000.00,15,@id);
INSERT INTO ram (component_id,ram_type,capacity_gb,stick_count,speed_mhz,cas_latency) 
VALUES (@id,'DDR5',16,1,5600,46) 
ON DUPLICATE KEY UPDATE ram_type='DDR5',capacity_gb=16,stick_count=1,speed_mhz=5600,cas_latency=46;

CALL upsert_component_base('RAM','Corsair Vengeance RGB 32GB (2x16GB) DDR5 6000MHz','Corsair','CMH32GX5M2B6000C30',52000.00,12,@id);
INSERT INTO ram (component_id,ram_type,capacity_gb,stick_count,speed_mhz,cas_latency) 
VALUES (@id,'DDR5',32,2,6000,30) 
ON DUPLICATE KEY UPDATE ram_type='DDR5',capacity_gb=32,stick_count=2,speed_mhz=6000,cas_latency=30;

CALL upsert_component_base('RAM','Kingston ValueRAM 32GB (1x32GB) DDR5 5600MHz','Kingston','KVR56U46BD8-32',56165.00,10,@id);
INSERT INTO ram (component_id,ram_type,capacity_gb,stick_count,speed_mhz,cas_latency) 
VALUES (@id,'DDR5',32,1,5600,46) 
ON DUPLICATE KEY UPDATE ram_type='DDR5',capacity_gb=32,stick_count=1,speed_mhz=5600,cas_latency=46;

-- ============================================================================
-- 4. GRAPHICS CARDS (GPUs) - Vishal Peripherals In-Stock
-- ============================================================================

CALL upsert_component_base('GPU','MSI GeForce RTX 3050 Ventus 2X E 6G','MSI','G3050V2XE6C',29250.00,15,@id);
INSERT INTO gpus (component_id,chipset,vram_gb,length_mm,tdp_watts,recommended_psu_wattage) 
VALUES (@id,'GeForce RTX 3050',6,205,70,450) 
ON DUPLICATE KEY UPDATE chipset='GeForce RTX 3050',vram_gb=6,length_mm=205,tdp_watts=70,recommended_psu_wattage=450;

CALL upsert_component_base('GPU','INNO3D GeForce RTX 5070 Twin X2 OC 12GB GDDR7','INNO3D','N50702-12D7X-175239N',95000.00,10,@id);
INSERT INTO gpus (component_id,chipset,vram_gb,length_mm,tdp_watts,recommended_psu_wattage) 
VALUES (@id,'GeForce RTX 5070',12,250,250,650) 
ON DUPLICATE KEY UPDATE chipset='GeForce RTX 5070',vram_gb=12,length_mm=250,tdp_watts=250,recommended_psu_wattage=650;

CALL upsert_component_base('GPU','Zotac Gaming GeForce RTX 5070 Twin Edge 12GB GDDR7','Zotac','ZT-B50700E-10P',95500.00,12,@id);
INSERT INTO gpus (component_id,chipset,vram_gb,length_mm,tdp_watts,recommended_psu_wattage) 
VALUES (@id,'GeForce RTX 5070',12,225,250,650) 
ON DUPLICATE KEY UPDATE chipset='GeForce RTX 5070',vram_gb=12,length_mm=225,tdp_watts=250,recommended_psu_wattage=650;

CALL upsert_component_base('GPU','Zotac Gaming GeForce RTX 5070 Twin Edge OC 12GB GDDR7','Zotac','ZT-B50700H-10P',96000.00,10,@id);
INSERT INTO gpus (component_id,chipset,vram_gb,length_mm,tdp_watts,recommended_psu_wattage) 
VALUES (@id,'GeForce RTX 5070',12,225,250,650) 
ON DUPLICATE KEY UPDATE chipset='GeForce RTX 5070',vram_gb=12,length_mm=225,tdp_watts=250,recommended_psu_wattage=650;

CALL upsert_component_base('GPU','MSI GeForce RTX 5070 Ventus 2X OC 12GB GDDR7','MSI','G5070-12V2C',97000.00,8,@id);
INSERT INTO gpus (component_id,chipset,vram_gb,length_mm,tdp_watts,recommended_psu_wattage) 
VALUES (@id,'GeForce RTX 5070',12,242,250,650) 
ON DUPLICATE KEY UPDATE chipset='GeForce RTX 5070',vram_gb=12,length_mm=242,tdp_watts=250,recommended_psu_wattage=650;

CALL upsert_component_base('GPU','Zotac Gaming GeForce RTX 5070 Solid OC 12GB GDDR7','Zotac','ZT-B50700J-10P',101000.00,8,@id);
INSERT INTO gpus (component_id,chipset,vram_gb,length_mm,tdp_watts,recommended_psu_wattage) 
VALUES (@id,'GeForce RTX 5070',12,270,250,650) 
ON DUPLICATE KEY UPDATE chipset='GeForce RTX 5070',vram_gb=12,length_mm=270,tdp_watts=250,recommended_psu_wattage=650;

CALL upsert_component_base('GPU','ASUS Prime GeForce RTX 5070 OC 12GB GDDR7','ASUS','PRIME-RTX5070-O12G',101000.00,6,@id);
INSERT INTO gpus (component_id,chipset,vram_gb,length_mm,tdp_watts,recommended_psu_wattage) 
VALUES (@id,'GeForce RTX 5070',12,268,250,650) 
ON DUPLICATE KEY UPDATE chipset='GeForce RTX 5070',vram_gb=12,length_mm=268,tdp_watts=250,recommended_psu_wattage=650;

CALL upsert_component_base('GPU','PNY GeForce RTX 5070 OC 12GB GDDR7','PNY','VCG507012TFXPB1-O',101000.00,6,@id);
INSERT INTO gpus (component_id,chipset,vram_gb,length_mm,tdp_watts,recommended_psu_wattage) 
VALUES (@id,'GeForce RTX 5070',12,248,250,650) 
ON DUPLICATE KEY UPDATE chipset='GeForce RTX 5070',vram_gb=12,length_mm=248,tdp_watts=250,recommended_psu_wattage=650;

CALL upsert_component_base('GPU','MSI GeForce RTX 5070 Ti Ventus 3X OC 16GB GDDR7','MSI','G5070T-16V3C',106999.00,5,@id);
INSERT INTO gpus (component_id,chipset,vram_gb,length_mm,tdp_watts,recommended_psu_wattage) 
VALUES (@id,'GeForce RTX 5070 Ti',16,308,300,750) 
ON DUPLICATE KEY UPDATE chipset='GeForce RTX 5070 Ti',vram_gb=16,length_mm=308,tdp_watts=300,recommended_psu_wattage=750;

-- ============================================================================
-- 5. STORAGE (NVMe SSDs) - In-Stock
-- ============================================================================

CALL upsert_component_base('STORAGE','Kingston NV2 1TB NVMe SSD','Kingston','SNV2S-1000G',5400.00,30,@id);
INSERT INTO storage (component_id,storage_type,form_factor,interface_type,capacity_gb,read_speed_mbps,write_speed_mbps) 
VALUES (@id,'SSD','M.2','PCIe NVMe Gen 4',1000,3500,2100) 
ON DUPLICATE KEY UPDATE storage_type='SSD',form_factor='M.2',interface_type='PCIe NVMe Gen 4',capacity_gb=1000,read_speed_mbps=3500,write_speed_mbps=2100;

CALL upsert_component_base('STORAGE','WD Blue SN580 1TB NVMe SSD','Western Digital','WDS100T3B0E',5950.00,25,@id);
INSERT INTO storage (component_id,storage_type,form_factor,interface_type,capacity_gb,read_speed_mbps,write_speed_mbps) 
VALUES (@id,'SSD','M.2','PCIe NVMe Gen 4',1000,4150,4150) 
ON DUPLICATE KEY UPDATE storage_type='SSD',form_factor='M.2',interface_type='PCIe NVMe Gen 4',capacity_gb=1000,read_speed_mbps=4150,write_speed_mbps=4150;

CALL upsert_component_base('STORAGE','Crucial P3 Plus 2TB NVMe SSD','Crucial','CT2000P3PSSD8',11900.00,18,@id);
INSERT INTO storage (component_id,storage_type,form_factor,interface_type,capacity_gb,read_speed_mbps,write_speed_mbps) 
VALUES (@id,'SSD','M.2','PCIe NVMe Gen 4',2000,5000,4200) 
ON DUPLICATE KEY UPDATE storage_type='SSD',form_factor='M.2',interface_type='PCIe NVMe Gen 4',capacity_gb=2000,read_speed_mbps=5000,write_speed_mbps=4200;

-- ============================================================================
-- 6. CPU COOLERS & SOCKET MAPPINGS - In-Stock
-- ============================================================================

CALL upsert_component_base('COOLER','DeepCool AG400 ARGB Single Tower','DeepCool','R-AG400-BKANMC-G-1',1950.00,25,@id);
INSERT INTO coolers (component_id,cooler_type,height_mm,radiator_size_mm,fan_size_mm,is_rgb) 
VALUES (@id,'AIR',150,NULL,120,TRUE) 
ON DUPLICATE KEY UPDATE cooler_type='AIR',height_mm=150,radiator_size_mm=NULL,fan_size_mm=120,is_rgb=TRUE;
DELETE FROM cooler_sockets WHERE cooler_id=@id;
INSERT INTO cooler_sockets (cooler_id,socket_type) VALUES (@id,'AM4'),(@id,'AM5'),(@id,'LGA1200'),(@id,'LGA1700');

CALL upsert_component_base('COOLER','DeepCool AG620 Dual Tower','DeepCool','R-AG620-BKNNMN-G-1',3950.00,20,@id);
INSERT INTO coolers (component_id,cooler_type,height_mm,radiator_size_mm,fan_size_mm,is_rgb) 
VALUES (@id,'AIR',157,NULL,120,FALSE) 
ON DUPLICATE KEY UPDATE cooler_type='AIR',height_mm=157,radiator_size_mm=NULL,fan_size_mm=120,is_rgb=FALSE;
DELETE FROM cooler_sockets WHERE cooler_id=@id;
INSERT INTO cooler_sockets (cooler_id,socket_type) VALUES (@id,'AM4'),(@id,'AM5'),(@id,'LGA1200'),(@id,'LGA1700'),(@id,'LGA1851');

CALL upsert_component_base('COOLER','MSI MAG CORELIQUID M360 ARGB','MSI','306-7ZW1B21-C51',7850.00,12,@id);
INSERT INTO coolers (component_id,cooler_type,height_mm,radiator_size_mm,fan_size_mm,is_rgb) 
VALUES (@id,'LIQUID',NULL,360,120,TRUE) 
ON DUPLICATE KEY UPDATE cooler_type='LIQUID',height_mm=NULL,radiator_size_mm=360,fan_size_mm=120,is_rgb=TRUE;
DELETE FROM cooler_sockets WHERE cooler_id=@id;
INSERT INTO cooler_sockets (cooler_id,socket_type) VALUES (@id,'AM4'),(@id,'AM5'),(@id,'LGA1200'),(@id,'LGA1700'),(@id,'LGA1851');

-- ============================================================================
-- 7. POWER SUPPLIES (PSUs) - In-Stock
-- ============================================================================

CALL upsert_component_base('PSU','MSI MAG A650BN 650W 80+ Bronze','MSI','MAG-A650BN',4250.00,25,@id);
INSERT INTO psus (component_id,wattage,efficiency_rating,form_factor,is_modular) 
VALUES (@id,650,'80+ Bronze','ATX',FALSE) 
ON DUPLICATE KEY UPDATE wattage=650,efficiency_rating='80+ Bronze',form_factor='ATX',is_modular=FALSE;

CALL upsert_component_base('PSU','Cooler Master MWE 750W Bronze V2','Cooler Master','MPE-7501-ACABW-BIN',5850.00,20,@id);
INSERT INTO psus (component_id,wattage,efficiency_rating,form_factor,is_modular) 
VALUES (@id,750,'80+ Bronze','ATX',FALSE) 
ON DUPLICATE KEY UPDATE wattage=750,efficiency_rating='80+ Bronze',form_factor='ATX',is_modular=FALSE;

CALL upsert_component_base('PSU','MSI MAG A850GL PCIE5 850W 80+ Gold','MSI','MAG-A850GL-PCIE5',8950.00,15,@id);
INSERT INTO psus (component_id,wattage,efficiency_rating,form_factor,is_modular) 
VALUES (@id,850,'80+ Gold','ATX',TRUE) 
ON DUPLICATE KEY UPDATE wattage=850,efficiency_rating='80+ Gold',form_factor='ATX',is_modular=TRUE;

-- ============================================================================
-- 8. PC CASES - In-Stock
-- ============================================================================

CALL upsert_component_base('CASE','MSI MAG FORGE 120R AIRFLOW','MSI','FORGE-120R',4150.00,20,@id);
INSERT INTO pc_cases (component_id,max_gpu_length_mm,max_cpu_cooler_height_mm,max_radiator_size_mm,supports_atx,supports_micro_atx,supports_mini_itx,max_120mm_fans,max_140mm_fans) 
VALUES (@id,330,160,240,TRUE,TRUE,TRUE,6,2) 
ON DUPLICATE KEY UPDATE max_gpu_length_mm=330,max_cpu_cooler_height_mm=160,max_radiator_size_mm=240,supports_atx=TRUE,supports_micro_atx=TRUE,supports_mini_itx=TRUE,max_120mm_fans=6,max_140mm_fans=2;

CALL upsert_component_base('CASE','Cooler Master MasterBox 520 Mesh','Cooler Master','MB520-KGNN-S00',6450.00,15,@id);
INSERT INTO pc_cases (component_id,max_gpu_length_mm,max_cpu_cooler_height_mm,max_radiator_size_mm,supports_atx,supports_micro_atx,supports_mini_itx,max_120mm_fans,max_140mm_fans) 
VALUES (@id,410,165,360,TRUE,TRUE,TRUE,7,4) 
ON DUPLICATE KEY UPDATE max_gpu_length_mm=410,max_cpu_cooler_height_mm=165,max_radiator_size_mm=360,supports_atx=TRUE,supports_micro_atx=TRUE,supports_mini_itx=TRUE,max_120mm_fans=7,max_140mm_fans=4;

-- ============================================================================
-- 9. CASE FANS - In-Stock
-- ============================================================================

CALL upsert_component_base('CASE_FAN','Cooler Master SickleFlow 120 ARGB','Cooler Master','MFX-B2DN-18NPA-R1',950.00,30,@id);
INSERT INTO case_fans (component_id,fan_size_mm,airflow_cfm,noise_db,is_rgb) 
VALUES (@id,120,62.0,27.0,TRUE) 
ON DUPLICATE KEY UPDATE fan_size_mm=120,airflow_cfm=62.0,noise_db=27.0,is_rgb=TRUE;

DROP PROCEDURE upsert_component_base;
COMMIT;

SELECT 
    category_type, 
    COUNT(*) AS component_count, 
    MIN(price) AS min_price_inr, 
    MAX(price) AS max_price_inr 
FROM components 
GROUP BY category_type 
ORDER BY category_type;
