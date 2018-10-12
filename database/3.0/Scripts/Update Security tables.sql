UPDATE tblformat set cataloguenumber = TRIM(cataloguenumber), digprodnum = TRIM(digprodnum)


UPDATE pm_security_user
set email = 'Farhan.Rasheed@sonymusic.com'
WHERE logon_name NOT IN ('giles03','venki01','smith57','ukan001');

UPDATE pm_product_manager
set email = 'Farhan.Rasheed@sonymusic.com';