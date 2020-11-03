SELECT  COUNT( DISTINCT cataloguenumber) cataloguenumber FROM tblformat WHERE cataloguenumber IS NOT NULL
-- 10293 P + 11626 D = 21919            23423 T - 21919      =1504 Nulls

SELECT  COUNT( DISTINCT digprodnum) cataloguenumber FROM tblformat WHERE digprodnum IS NOT NULL

SELECT COUNT(*) FROM tblformat 

SELECT COUNT(DISTINCT catalogue_num) FROM pm_detail_physical WHERE css_physical_id IS NOT NULL
-- 3732

SELECT COUNT(DISTINCT grid_number) FROM pm_detail_digital WHERE css_digital_id IS NOT NULL
-- 8265

SELECT COUNT(DISTINCT mobile_grid_number) FROM pm_track_listing_digital WHERE css_digital_id IS NOT NULL
-- 1458

-- Total matched 1458 + 8265 + 3732 = 13455




