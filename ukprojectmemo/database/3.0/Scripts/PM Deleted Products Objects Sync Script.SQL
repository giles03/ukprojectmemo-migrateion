--------------------------------------------------------------------------
-- Play this script in PMEMO2_UAT@DEV1 to make it look like PMEMO2@DEV1
--
-- Please review the script before using it to make sure it won't
-- cause any unacceptable data loss.
--
-- PMEMO2_UAT@DEV1 Schema Extracted by User PMEMO2_UAT 
-- PMEMO2@DEV1 Schema Extracted by User PMEMO2 
--------------------------------------------------------------------------
-- "Set define off" turns off substitution variables
Set define off;

CREATE SEQUENCE PMEMO2.SEQ_DELETED_PRODUCT_ID
  START WITH 1
  MAXVALUE 999999999999999999999999999
  MINVALUE 0
  NOCYCLE
  NOCACHE
  NOORDER;

CREATE TABLE PMEMO2.PM_DELETED_PRODUCTS
(
  PM_DP_ID        NUMBER(10)                    NOT NULL,
  PM_REF_ID       NUMBER(20)                    NOT NULL,
  PM_REVISION_ID  NUMBER(5)                     NOT NULL,
  PM_DETAIL_ID    NUMBER(5)                     NOT NULL,
  MEMO_CATEGORY   CHAR(1 BYTE)                  NOT NULL,
  TRACK_NUM       NUMBER(5),
  CSS_ID          NUMBER(20),
  CREATE_DATE     DATE
);

CREATE UNIQUE INDEX PMEMO2.XPKPM_DELETED_PRODUCTS ON PMEMO2.PM_DELETED_PRODUCTS
(PM_DP_ID);

ALTER TABLE PMEMO2.PM_DELETED_PRODUCTS
 ADD CONSTRAINT XPKPM_DELETED_PRODUCTS
  PRIMARY KEY
  (PM_DP_ID)
  USING INDEX PMEMO2.XPKPM_DELETED_PRODUCTS;

CREATE OR REPLACE PROCEDURE PMEMO2.LOAD_CSS_DATA
IS
   CURSOR c1
   IS
      SELECT pm_ref_id,
             MAX(pm_revision_id) pm_revision_id,
             pm_detail_id,
             track_num,
             UPPER(TRIM(mobile_grid_number)) cataloguenumber,
             css_digital_id,
             'T' tab
        FROM pm_track_listing_digital dt
       WHERE     mobile_grid_number IS NOT NULL
       GROUP BY pm_ref_id,
             pm_detail_id,
             track_num,
             UPPER(TRIM(mobile_grid_number)) ,
             css_digital_id,
             'T'
      UNION
      SELECT pm_ref_id,
             MAX(pm_revision_id),
             pm_detail_id,
             NULL,
            UPPER( TRIM(grid_number)),
             css_digital_id,
             'D'
        FROM pm_detail_digital dg
       WHERE     grid_number IS NOT NULL
           GROUP BY  pm_ref_id,
             pm_detail_id,
             NULL,
            UPPER( TRIM(grid_number)),
             css_digital_id,
             'D'
      UNION
      SELECT pm_ref_id,
            MAX(pm_revision_id),
             pm_detail_id,
             NULL,
             UPPER(TRIM(catalogue_num)),
             css_physical_id,
             'P'
        FROM pm_detail_physical ph
       WHERE  catalogue_num IS NOT NULL
             GROUP BY
              pm_ref_id,
               pm_detail_id,
             NULL,
             UPPER(TRIM(catalogue_num)),
             css_physical_id,
             'P'
             ;

   TYPE t_c1 IS TABLE OF c1%ROWTYPE
      INDEX BY PLS_INTEGER;


CURSOR c2 IS
 SELECT A.*, b.releaseid release_id, b.releasetitle
 FROM tblformat A, tblproject b
 WHERE A.releaseid = b.releaseid(+)   ;

TYPE t_tblformat IS TABLE OF c2%ROWTYPE;

   l_tblformat_p   t_tblformat;
   l_tblformat_d   t_tblformat;


   l_c1          t_c1;
   v_css_id      NUMBER (20);
   v_error       VARCHAR2 (1000);
/*CURSOR c1 IS

SELECT *
FROM tblformat T
WHERE T.formatid = (SELECT MAX(formatid) FROM tblformat y WHERE T.cataloguenumber = y.cataloguenumber) ;*/

/******************************************************************************
   NAME:       LOAD_CSS_DATA
   PURPOSE:

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        11/01/2013   farh02       1. Created this procedure.

   NOTES:


******************************************************************************/
BEGIN
   SELECT T.*,U.releaseid, U.releasetitle
     BULK COLLECT INTO l_tblformat_p
     FROM tblformat T, tblproject U
    WHERE  T.releaseid = U.releaseid(+)   AND  T.formatid = (SELECT MAX (formatid)
                          FROM tblformat y
                         WHERE T.cataloguenumber = y.cataloguenumber);

   SELECT T.*,U.releaseid, U.releasetitle
     BULK COLLECT INTO l_tblformat_d
     FROM tblformat T, tblproject U
    WHERE  T.releaseid = U.releaseid(+)   AND  T.formatid = (SELECT MAX (formatid)
                          FROM tblformat y
                         WHERE T.digprodnum  = y.digprodnum);

-- Get all physical and digital product numbers in memo
   OPEN c1;

   FETCH c1
      BULK COLLECT INTO l_c1;


-- Match physical product numbers

   FOR i IN 1 .. l_tblformat_p.COUNT
   LOOP
      FOR j IN 1 .. l_c1.COUNT
      LOOP
        --
            -- dbms_output.put_line(l_tblformat_p(i).cataloguenumber );
            --  INSERT INTO temp_test (desc1) VALUES (l_tblformat_p(i).cataloguenumber);
            IF l_c1 (j).tab = 'P' AND  UPPER(TRIM(l_tblformat_p (i).cataloguenumber)) = l_c1 (j).cataloguenumber
            THEN
               BEGIN
                  SELECT seq_css_id.NEXTVAL INTO v_css_id FROM DUAL;

                  INSERT
                    INTO pm_detail_physical_css (css_physical_id,
                                                supplementary_title,
                                                 manufacturer,
                                                 repro_supplier,
                                                 stock_due,
                                                 general_notes,
                                                 label_copy_received,
                                                 label_copy_notes,
                                                 masters_received,
                                                 masters_test_received,
                                                 masters_dispatch_method,
                                                 masters_dispatch,
                                                 masters_notes,
                                                 artwork_received,
                                                 artwork_dispatch_method,
                                                 artwork_notes,
                                                 destination,
                                                final_artwork_approved,
                                                test_approval,
                                                upload_artwork,
                                                created_by,
                                                created_date,
                                                last_updated_by,
                                                last_update_date,
                                                is_pkg_form_approved,
                                                is_pkg_form_received,
                                                proofs_sent    )
                  VALUES (v_css_id,
                          l_tblformat_p (i).releasetitle,
                          l_tblformat_p (i).manufacturername,
                          l_tblformat_p (i).reprosuppliername,
                          l_tblformat_p (i).stockduedate,
                          l_tblformat_p (i).generalnotes,
                          l_tblformat_p (i).labelcopyreceiveddate,
                          l_tblformat_p (i).labelcopynotes,
                          l_tblformat_p (i).mastersreceiveddate,
                          CASE l_tblformat_p (i).masterstestrecind  WHEN '0' THEN 'N'  WHEN '1' THEN 'Y'  ELSE l_tblformat_p (i).masterstestrecind   END,
                          l_tblformat_p (i).mastersdispatchmethodid,
                          l_tblformat_p (i).mastersdispatchdate,
                          l_tblformat_p (i).mastersnotes,
                          l_tblformat_p (i).artworkreceiveddate,
                          l_tblformat_p (i).artworkdispatchmethodid,
                          l_tblformat_p (i).artworknotes,
                          l_tblformat_p (i).artworkdispatchdestination,
                          l_tblformat_p (i).finalproofapprovaldate,
                          l_tblformat_p (i).masterstestappdesc,
                          l_tblformat_p (i).cromalinproofsreceiveddate,
                          l_tblformat_p (i).createusername,
                          l_tblformat_p (i).createdate,
                          l_tblformat_p (i).lastchangeusername,
                          l_tblformat_p (i).lastchangeddate,
                          CASE l_tblformat_p (i).packageformapproved  WHEN '0' THEN 'N'  WHEN '1' THEN 'Y'  ELSE l_tblformat_p (i).packageformapproved   END,
                          CASE l_tblformat_p (i).packageformreceived  WHEN '0' THEN 'N'  WHEN '1' THEN 'Y'  ELSE l_tblformat_p (i).packageformreceived   END,
                           l_tblformat_p (i).filmssentdate
                          );

                  UPDATE pm_detail_physical
                     SET css_physical_id = v_css_id
                   WHERE     pm_ref_id = l_c1 (j).pm_ref_id
                         AND pm_revision_id <= l_c1 (j).pm_revision_id
                         AND pm_detail_id = l_c1 (j).pm_detail_id;
               EXCEPTION
                  WHEN OTHERS
                  THEN
                     v_error := SQLERRM;

                     INSERT INTO tblformat_excep
                          VALUES (l_tblformat_p (i).formatid, v_error);
               END;
            END IF;

      END LOOP;
   END LOOP;


   FOR i IN 1 .. l_tblformat_d.COUNT
   LOOP
      FOR j IN 1 .. l_c1.COUNT
      LOOP

         IF l_c1 (j).tab = 'T' AND  UPPER(TRIM(l_tblformat_d (i).digprodnum)) = l_c1 (j).cataloguenumber THEN
               BEGIN
                  SELECT seq_css_id.NEXTVAL INTO v_css_id FROM DUAL;

                  INSERT INTO pm_detail_digital_css (css_digital_id,
                                                     supplementary_title,
                                                     general_notes,
                                                     label_copy_received,
                                                     label_copy_notes,
                                                     masters_received,
                                                     masters_dispatch_method,
                                                     masters_dispatch,
                                                     masters_notes,
                                                     artwork_received,
                                                     artwork_dispatch_method,
                                                     artwork_notes,
                                                     upload_artwork,
                                                     created_by,
                                                    created_date,
                                                    last_updated_by,
                                                    last_update_date)
                       VALUES (v_css_id,
                               l_tblformat_d (i).releasetitle,
                               l_tblformat_d (i).generalnotes,
                               l_tblformat_d (i).labelcopyreceiveddate,
                               l_tblformat_d (i).labelcopynotes,
                               l_tblformat_d (i).mastersreceiveddate,
                               l_tblformat_d (i).mastersdispatchmethodid,
                               l_tblformat_d (i).mastersdispatchdate,
                               l_tblformat_d (i).mastersnotes,
                               l_tblformat_d (i).artworkreceiveddate,
                               l_tblformat_d (i).artworkdispatchmethodid,
                               l_tblformat_d (i).artworknotes,
                               l_tblformat_d (i).cromalinproofsreceiveddate,
                               l_tblformat_d (i).createusername,
                              l_tblformat_d (i).createdate,
                              l_tblformat_d (i).lastchangeusername,
                              l_tblformat_d (i).lastchangeddate
                               );

                  UPDATE pm_track_listing_digital
                     SET css_digital_id = v_css_id
                   WHERE     pm_ref_id = l_c1 (j).pm_ref_id
                         AND pm_revision_id <= l_c1 (j).pm_revision_id
                         AND pm_detail_id = l_c1 (j).pm_detail_id
                         AND track_num = l_c1 (j).track_num;
               EXCEPTION
                  WHEN OTHERS
                  THEN
                     v_error := SQLERRM;

                     INSERT INTO tblformat_excep
                          VALUES (l_tblformat_d (i).formatid, v_error);
               END;
            ELSIF l_c1 (j).tab = 'D' AND  UPPER(TRIM(l_tblformat_d (i).digprodnum)) = l_c1 (j).cataloguenumber
            THEN
               BEGIN
                  SELECT seq_css_id.NEXTVAL INTO v_css_id FROM DUAL;

                  INSERT INTO pm_detail_digital_css (css_digital_id,
                                                     supplementary_title,
                                                     general_notes,
                                                     label_copy_received,
                                                     label_copy_notes,
                                                     masters_received,
                                                     masters_dispatch_method,
                                                     masters_dispatch,
                                                     masters_notes,
                                                     artwork_received,
                                                     artwork_dispatch_method,
                                                     artwork_notes,
                                                     upload_artwork,
                                                      created_by,
                                                    created_date,
                                                    last_updated_by,
                                                    last_update_date)
                       VALUES (v_css_id,
                               l_tblformat_d (i).releasetitle,
                               l_tblformat_d (i).generalnotes,
                               l_tblformat_d (i).labelcopyreceiveddate,
                               l_tblformat_d (i).labelcopynotes,
                               l_tblformat_d (i).mastersreceiveddate,
                               l_tblformat_d (i).mastersdispatchmethodid,
                               l_tblformat_d (i).mastersdispatchdate,
                               l_tblformat_d (i).mastersnotes,
                               l_tblformat_d (i).artworkreceiveddate,
                               l_tblformat_d (i).artworkdispatchmethodid,
                               l_tblformat_d (i).artworknotes,
                               l_tblformat_d (i).cromalinproofsreceiveddate,
                               l_tblformat_d (i).createusername,
                              l_tblformat_d (i).createdate,
                              l_tblformat_d (i).lastchangeusername,
                              l_tblformat_d (i).lastchangeddate
                               );

                  UPDATE pm_detail_digital
                     SET css_digital_id = v_css_id
                   WHERE     pm_ref_id = l_c1 (j).pm_ref_id
                         AND pm_revision_id <= l_c1 (j).pm_revision_id
                         AND pm_detail_id = l_c1 (j).pm_detail_id;
               EXCEPTION
                  WHEN OTHERS
                  THEN
                     v_error := SQLERRM;

                     INSERT INTO tblformat_excep
                          VALUES (l_tblformat_d (i).formatid, v_error);
               END;
          END IF;
      END LOOP;
   END LOOP;





   CLOSE c1;

   COMMIT;
EXCEPTION
   WHEN NO_DATA_FOUND
   THEN
      NULL;
   WHEN OTHERS
   THEN
      -- Consider logging the error and then re-raise
      RAISE;
END LOAD_CSS_DATA;
/

SHOW ERRORS;

CREATE OR REPLACE PROCEDURE PMEMO2.PM_DELETED_PRODUCTS_LOG (p_ref_id IN NUMBER, p_max_revision IN NUMBER, p_prev_revision IN NUMBER) IS
   v_prev_revision   NUMBER (5);

/******************************************************************************
   NAME:       PM_DELETED_PRODUCTS
   PURPOSE:

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        12/03/2013   farh02       1. Created this procedure.

   NOTES:

******************************************************************************/

BEGIN


-- Find deleted products - Physical

INSERT INTO pm_deleted_products (pm_dp_id, pm_ref_id, pm_revision_id, pm_detail_id, memo_category, track_num, css_id, create_date)
SELECT   seq_deleted_product_id.NEXTVAL ,A.pm_ref_id, p_prev_revision, b.pm_detail_id, 'P', NULL, A.css_physical_id, SYSDATE
FROM pm_detail_physical A JOIN ( SELECT pm_ref_id, pm_detail_id
FROM pm_detail_physical
WHERE pm_ref_id  = p_ref_id AND pm_revision_id = p_prev_revision
MINUS
SELECT pm_ref_id, pm_detail_id
FROM pm_detail_physical
WHERE pm_ref_id  = p_ref_id AND pm_revision_id = p_max_revision) b ON (A.pm_ref_id = b.pm_ref_id AND A.pm_detail_id = b.pm_detail_id AND A.pm_revision_id =p_prev_revision )
;

-- Find deleted products - Digital
INSERT INTO pm_deleted_products (pm_dp_id, pm_ref_id, pm_revision_id, pm_detail_id, memo_category, track_num, css_id, create_date)
SELECT seq_deleted_product_id.NEXTVAL, A.pm_ref_id, p_prev_revision, b.pm_detail_id, 'D', NULL, A.css_digital_id, SYSDATE
FROM pm_detail_digital A JOIN ( SELECT pm_ref_id, pm_detail_id
FROM pm_detail_digital
WHERE pm_ref_id  = p_ref_id AND pm_revision_id = p_prev_revision
MINUS
SELECT pm_ref_id, pm_detail_id
FROM pm_detail_digital
WHERE pm_ref_id  = p_ref_id AND pm_revision_id = p_max_revision) b ON (A.pm_ref_id = b.pm_ref_id AND A.pm_detail_id = b.pm_detail_id AND A.pm_revision_id =p_prev_revision )
;

-- Find deleted tracks/mobile products - digital
INSERT INTO pm_deleted_products (pm_dp_id, pm_ref_id, pm_revision_id, pm_detail_id, memo_category, track_num, css_id, create_date)
SELECT seq_deleted_product_id.NEXTVAL, A.pm_ref_id, p_prev_revision, b.pm_detail_id, 'D', A.track_num, A.css_digital_id,  SYSDATE
FROM pm_track_listing_digital A JOIN ( SELECT pm_ref_id, pm_detail_id, track_num
FROM pm_track_listing_digital
WHERE pm_ref_id  = p_ref_id AND pm_revision_id = p_prev_revision
MINUS
SELECT  pm_ref_id, pm_detail_id, track_num
FROM pm_track_listing_digital
WHERE pm_ref_id  = p_ref_id AND pm_revision_id = p_max_revision) b ON (A.pm_ref_id = b.pm_ref_id  AND A.pm_detail_id = b.pm_detail_id   AND A.track_num=b.track_num    AND A.pm_revision_id= p_prev_revision ) ;


-- Find deleted tracks    - Physical
INSERT INTO pm_deleted_products (pm_dp_id, pm_ref_id, pm_revision_id, pm_detail_id, memo_category, track_num, css_id, create_date)
SELECT seq_deleted_product_id.NEXTVAL, A.pm_ref_id, p_prev_revision, b.pm_detail_id, 'P', A.track_num, NULL, SYSDATE
FROM pm_track_listing_physical A JOIN ( SELECT pm_ref_id, pm_detail_id, track_num
FROM pm_track_listing_physical
WHERE pm_ref_id  = p_ref_id AND pm_revision_id = p_prev_revision
MINUS
SELECT  pm_ref_id, pm_detail_id, track_num
FROM pm_track_listing_physical
WHERE pm_ref_id  = p_ref_id AND pm_revision_id = p_max_revision) b ON (A.pm_ref_id = b.pm_ref_id  AND A.pm_detail_id = b.pm_detail_id   AND A.track_num=b.track_num    AND A.pm_revision_id= p_prev_revision ) ;


COMMIT;


EXCEPTION
   WHEN NO_DATA_FOUND THEN
      NULL;
   WHEN OTHERS THEN
      -- Consider logging the error and then re-raise
      RAISE;
END PM_DELETED_PRODUCTS_LOG;
/

SHOW ERRORS;

CREATE OR REPLACE PROCEDURE PMEMO2.project_memo_save1 (p_ref_id IN NUMBER) IS
   r_pmemo_header                 pm_header%ROWTYPE;
   e_no_ref_id_header             EXCEPTION;
   e_physical_insert_failure      EXCEPTION;
   e_digital_insert_failure       EXCEPTION;
   e_promos_insert_failure        EXCEPTION;
   e_phys_track_insert_failure    EXCEPTION;
   e_digi_track_insert_failure    EXCEPTION;
   e_promo_track_insert_failure   EXCEPTION;
   e_duplicate_header_row         EXCEPTION;
   e_fatal_error                  EXCEPTION;

   PRAGMA EXCEPTION_INIT (e_duplicate_header_row, -00001);
   v_error                        VARCHAR2 (500);
   v_max_revision                 NUMBER (5);
   v_dummy                        CHAR (1);
   v_is_new_memo                  BOOLEAN DEFAULT FALSE;
   v_project_number               VARCHAR2 (20);
   v_gcls_number                  VARCHAR2 (20);
   v_prev_revision                pm_header.pm_revision_id%TYPE;
   v_prev_cat_num                 VARCHAR2 (20);
   v_prev_loc_cat_num             VARCHAR2 (20);
   v_prev_grid_num                VARCHAR2 (20);
   v_prev_isrc_num                VARCHAR2 (20);

   CURSOR c_pm_physical (p_revision IN NUMBER) IS
      SELECT pm_ref_id,
             pm_revision_id,
             pm_detail_id,
             TRIM (catalogue_num) catalogue_num,
             cat_assign_date,
             TRIM (local_cat_num) local_cat_num,
             local_cat_date,
             is_import,
             is_uk_sticker,
             is_shrinkwrap_required,
             is_insert_requirement,
             is_limited_edition,
             sticker_requirements,
             limited_qty,
             release_date,
             pack_spec_comment,
             num_of_discs,
             comments,
             price_line_id,
             sticker_period_id,
             pack_spec_id,
             prod_format_id,
             digital_equivalent,
             TRIM (barcode) barcode,
             monis_status,
             reldt_warn,
             is_tv_advertised,
             dealer_price,
             sticker_pos_id,
             is_dig_equiv,
             is_intl_rel,
             de_barcode,
             pm_detail_link,
             exclusive_detail,
             exclusive_to,
             is_exclusive,
             vmp,
             supplementary_title          ,
             css_physical_id
        FROM pm_draft_physical
       WHERE pm_ref_id = p_ref_id AND pm_revision_id = p_revision;

   CURSOR c_pm_digital (p_revision IN NUMBER) IS
      SELECT pm_ref_id,
             pm_revision_id,
             pm_detail_id,
             catalogue_num,
             cat_assign_date,
             release_date,
             is_exclusive,
             exclusive_to,
             exclusive_detail,
             is_new_artwork,
             is_ringtone_approval,
             TRIM (grid_number) grid_number,
             grid_num_date,
             comments,
             prod_format_id,
             combo_ref,
             TRIM (barcode) barcode,
             monis_status,
             reldt_warn,
             is_tv_advertised,
             dealer_price,
             is_intl_rel,
             is_pre_order,
             is_vid_stream,
             preview_clips,
             preview_rel_date,
             is_audio_stream,
             audio_stream_date,
             pm_detail_link,
             supplementary_title,
             css_digital_id
        FROM pm_draft_digital
       WHERE pm_ref_id = p_ref_id AND pm_revision_id = p_revision;

   CURSOR c_pm_promos (p_revision IN NUMBER) IS
      SELECT pm_ref_id,
             pm_revision_id,
             pm_detail_id,
             TRIM (catalogue_num) catalogue_num,
             cat_assign_date,
             TRIM (local_cat_num) local_cat_num,
             local_cat_date,
             pack_spec_comment,
             num_of_discs,
             parts_due_date,
             stock_req_date,
             comments,
             prod_format_id,
             monis_status
        FROM pm_draft_promos
       WHERE pm_ref_id = p_ref_id AND pm_revision_id = p_revision;

   CURSOR c_physical_tracks (p_revision IN NUMBER) IS
      SELECT pm_ref_id,
             pm_revision_id,
             pm_detail_id,
             track_num,
             track_name,
             comments,
             TRIM (isrc_number) isrc_number,
             isrc_num_date,
             track_order
        FROM pm_draft_physical_tracks
       WHERE pm_ref_id = p_ref_id AND pm_revision_id = p_revision;

   CURSOR c_digital_tracks (p_revision IN NUMBER) IS
      SELECT pm_ref_id,
             pm_revision_id,
             pm_detail_id,
             track_num,
             track_name,
             comments,
             TRIM (isrc_number) isrc_number,
             isrc_num_date,
             TRIM (mobile_grid_number) mobile_grid_number,
             track_order,
             pre_order_only,
             monis_status,
             css_digital_id
        FROM pm_draft_digital_tracks
       WHERE pm_ref_id = p_ref_id AND pm_revision_id = p_revision;

   CURSOR c_promo_tracks (p_revision IN NUMBER) IS
      SELECT pm_ref_id,
             pm_revision_id,
             pm_detail_id,
             track_num,
             track_name,
             comments,
             TRIM (isrc_number) isrc_number,
             isrc_num_date,
             track_order
        FROM pm_draft_promo_tracks
       WHERE pm_ref_id = p_ref_id AND pm_revision_id = p_revision;

   CURSOR c_mobile_tracks (p_revision IN NUMBER) IS
      SELECT pm_ref_id,
             pm_revision_id,
             track_num,
             pm_detail_id,
             mobile_track_id,
             TRIM (mobile_cat_num) mobile_cat_num,
             exclusive_detail,
             mob_prod_id,
             TRIM (isrc_number) isrc_number,
             isrc_num_date,
             mobile_cat_num_date,
             track_order
        FROM pm_draft_mobile_tracks
       WHERE pm_ref_id = p_ref_id AND pm_revision_id = p_revision;

/******************************************************************************
   NAME:       PROJECT_MEMO_SAVE
   PURPOSE:
   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        02-Sep-2008          1. Created this procedure.
   1.1        05-Sep-208           2. Modified procedure to distinguish between new or edited version save
   1.2        23-Apr-2009          3. Added Track Order to all track listing tables
   NOTES:
      This procedure saves the project memo to main tables from draft tables.
******************************************************************************/

BEGIN
   -- Get the maximum revision no. for given project memo reference no.

   SELECT NVL (MAX (pm_revision_id), 0)
     INTO v_max_revision
     FROM pm_draft_header
    WHERE pm_ref_id = p_ref_id;

   IF v_max_revision = 0 THEN
      RAISE e_no_ref_id_header;
   END IF;


   -- Get last revision number from PM_HEADER

   SELECT NVL (MAX (pm_revision_id), 0)
     INTO v_prev_revision
     FROM pm_header
    WHERE pm_ref_id = p_ref_id;

   -- No previous revision found, set is new memo flag to TRUE

   IF v_prev_revision = 0 THEN
      v_is_new_memo := TRUE;
   END IF;


   -- Change status of existing errors for this project memo revision to 'N'

   UPDATE pm_save_exception
      SET is_current = 'N'
    WHERE pm_ref_id = p_ref_id AND pm_rev_id = v_max_revision;

   SELECT P.pm_ref_id,
          P.pm_revision_id,
          P.submit_date,
          P.submit_by,
          P.is_local_act,
          P.product_title,
          P.supp_product_title,
          P.is_uk_gen_parts,
          P.is_parental_advisory,
          P.is_full_album_delivery,
          TRIM (P.project_number) project_number,
          P.project_num_date,
          TRIM (P.gcls_number) gcls_number,
          P.gcls_num_date,
          P.revision_comment,
          P.logo_file_location,
          P.is_digital,
          P.is_physical,
          P.is_promo,
          P.is_being_edited,
          P.local_label_id,
          P.prod_type_id,
          P.genre_id,
          P.local_genre_id,
          P.dist_right_id,
          P.repertoire_owner_id,
          P.artist_id,
          P.uk_label_grp_id,
          P.prod_mgr_id,
          P.edited_by,
          P.edit_date,
          P.is_joint_venture,
          P.monis_status,
          P.mkt_label_id
     INTO r_pmemo_header
     FROM pm_draft_header P
    WHERE P.pm_ref_id = p_ref_id AND P.pm_revision_id = v_max_revision;

   IF NOT v_is_new_memo THEN
      BEGIN
         SELECT project_number, gcls_number
           INTO v_project_number, v_gcls_number
           FROM pm_header
          WHERE     pm_ref_id = p_ref_id
                AND pm_revision_id = (SELECT MAX (pm_revision_id)
                                        FROM pm_header
                                       WHERE pm_ref_id = p_ref_id);
      EXCEPTION
         WHEN NO_DATA_FOUND THEN
            NULL;
      END;
   END IF;

   -- Update project number assigned date if it has been modified since last version
   IF    (v_project_number IS NULL AND r_pmemo_header.project_number IS NOT NULL)
      OR (v_project_number IS NOT NULL AND r_pmemo_header.project_number IS NULL)
      OR (    v_project_number IS NOT NULL
          AND r_pmemo_header.project_number IS NOT NULL
          AND v_project_number != r_pmemo_header.project_number) THEN
      r_pmemo_header.project_num_date := CURRENT_DATE;
   END IF;

   -- Update gcls number assigned date if it has been modified since last version
   IF    (v_gcls_number IS NULL AND r_pmemo_header.gcls_number IS NOT NULL)
      OR (v_gcls_number IS NOT NULL AND r_pmemo_header.gcls_number IS NULL)
      OR (    v_gcls_number IS NOT NULL
          AND r_pmemo_header.gcls_number IS NOT NULL
          AND v_gcls_number != r_pmemo_header.gcls_number) THEN
      r_pmemo_header.gcls_num_date := CURRENT_DATE;
   END IF;



   IF v_is_new_memo THEN
      -- if its a new project memo assign submit by user to edited by user
      r_pmemo_header.edited_by := r_pmemo_header.submit_by;
      r_pmemo_header.submit_date := CURRENT_DATE;
   ELSE
      r_pmemo_header.edit_date := CURRENT_DATE;
   END IF;


   BEGIN
      INSERT INTO pm_header
           VALUES r_pmemo_header;
   EXCEPTION
      WHEN e_duplicate_header_row THEN
         INSERT INTO pm_save_exception
              VALUES (p_ref_id,
                      v_max_revision,
                      'Primary Key violated PM_HEADER.',
                      CURRENT_DATE,
                      'Y');

         COMMIT;
         RAISE e_fatal_error;
   END;


   --          *********  Insert Physical Detail Records into PM_DETAIL_PHYSICAL

   BEGIN
      FOR l_rec IN c_pm_physical (v_max_revision) LOOP
         IF NOT v_is_new_memo THEN
            BEGIN
               -- Try to get fields from previous revision so that current date may be assigned
               SELECT catalogue_num, local_cat_num
                 INTO v_prev_cat_num, v_prev_loc_cat_num
                 FROM pm_detail_physical
                WHERE pm_ref_id = p_ref_id AND pm_revision_id = v_prev_revision AND pm_detail_id = l_rec.pm_detail_id;

               IF    (v_prev_cat_num IS NULL AND l_rec.catalogue_num IS NOT NULL)
                  OR (v_prev_cat_num IS NOT NULL AND l_rec.catalogue_num IS NULL)
                  OR (    v_prev_cat_num IS NOT NULL
                      AND l_rec.catalogue_num IS NOT NULL
                      AND v_prev_cat_num != l_rec.catalogue_num) THEN
                  l_rec.cat_assign_date := CURRENT_DATE;
               END IF;

               IF    (v_prev_loc_cat_num IS NULL AND l_rec.local_cat_num IS NOT NULL)
                  OR (v_prev_loc_cat_num IS NOT NULL AND l_rec.local_cat_num IS NULL)
                  OR (    v_prev_loc_cat_num IS NOT NULL
                      AND l_rec.local_cat_num IS NOT NULL
                      AND v_prev_loc_cat_num != l_rec.local_cat_num) THEN
                  l_rec.local_cat_date := CURRENT_DATE;
               END IF;
            EXCEPTION
               WHEN NO_DATA_FOUND THEN
                  NULL;
            END;
         END IF;

         INSERT INTO pm_detail_physical
              VALUES l_rec;
      END LOOP;
   EXCEPTION
      WHEN OTHERS THEN
         ROLLBACK;
         v_error := SQLERRM;
         RAISE e_physical_insert_failure;
   END;

   --      *********  Insert Digital Detail Records into PM_DETAIL_DIGITAL

   BEGIN
      FOR l_rec IN c_pm_digital (v_max_revision) LOOP
         IF NOT v_is_new_memo THEN
            BEGIN
               -- Try to get fields from previous revision so that current date may be assigned
               SELECT catalogue_num, grid_number
                 INTO v_prev_cat_num, v_prev_grid_num
                 FROM pm_detail_digital
                WHERE pm_ref_id = p_ref_id AND pm_revision_id = v_prev_revision AND pm_detail_id = l_rec.pm_detail_id;

               IF    (v_prev_cat_num IS NULL AND l_rec.catalogue_num IS NOT NULL)
                  OR (v_prev_cat_num IS NOT NULL AND l_rec.catalogue_num IS NULL)
                  OR (    v_prev_cat_num IS NOT NULL
                      AND l_rec.catalogue_num IS NOT NULL
                      AND v_prev_cat_num != l_rec.catalogue_num) THEN
                  l_rec.cat_assign_date := CURRENT_DATE;
               END IF;

               IF    (v_prev_grid_num IS NULL AND l_rec.grid_number IS NOT NULL)
                  OR (v_prev_grid_num IS NOT NULL AND l_rec.grid_number IS NULL)
                  OR (    v_prev_grid_num IS NOT NULL
                      AND l_rec.grid_number IS NOT NULL
                      AND v_prev_grid_num != l_rec.grid_number) THEN
                  l_rec.grid_num_date := CURRENT_DATE;
               END IF;
            EXCEPTION
               WHEN NO_DATA_FOUND THEN
                  NULL;
            END;
         END IF;

         INSERT INTO pm_detail_digital
              VALUES l_rec;
      END LOOP;
   EXCEPTION
      WHEN OTHERS THEN
         ROLLBACK;
         v_error := SQLERRM;
         RAISE e_digital_insert_failure;
   END;

   --      *********  Insert Promo Detail Records into PM_DETAIL_PROMOS

   BEGIN
      FOR l_rec IN c_pm_promos (v_max_revision) LOOP
         IF NOT v_is_new_memo THEN
            BEGIN
               -- Try to get fields from previous revision so that current date may be assigned
               SELECT catalogue_num, local_cat_num
                 INTO v_prev_cat_num, v_prev_loc_cat_num
                 FROM pm_detail_promos
                WHERE pm_ref_id = p_ref_id AND pm_revision_id = v_prev_revision AND pm_detail_id = l_rec.pm_detail_id;

               IF    (v_prev_cat_num IS NULL AND l_rec.catalogue_num IS NOT NULL)
                  OR (v_prev_cat_num IS NOT NULL AND l_rec.catalogue_num IS NULL)
                  OR (    v_prev_cat_num IS NOT NULL
                      AND l_rec.catalogue_num IS NOT NULL
                      AND v_prev_cat_num != l_rec.catalogue_num) THEN
                  l_rec.cat_assign_date := CURRENT_DATE;
               END IF;

               IF    (v_prev_loc_cat_num IS NULL AND l_rec.local_cat_num IS NOT NULL)
                  OR (v_prev_loc_cat_num IS NOT NULL AND l_rec.local_cat_num IS NULL)
                  OR (    v_prev_loc_cat_num IS NOT NULL
                      AND l_rec.local_cat_num IS NOT NULL
                      AND v_prev_loc_cat_num != l_rec.local_cat_num) THEN
                  l_rec.local_cat_date := CURRENT_DATE;
               END IF;
            EXCEPTION
               WHEN NO_DATA_FOUND THEN
                  NULL;
            END;
         END IF;


         INSERT INTO pm_detail_promos
              VALUES l_rec;
      END LOOP;
   EXCEPTION
      WHEN OTHERS THEN
         ROLLBACK;
         v_error := SQLERRM;
         RAISE e_promos_insert_failure;
   END;



   BEGIN
      FOR l_rec IN c_physical_tracks (v_max_revision) LOOP
         INSERT INTO pm_track_listing_physical
              VALUES l_rec;
      END LOOP;
   EXCEPTION
      WHEN OTHERS THEN
         ROLLBACK;
         v_error := SQLERRM;
         RAISE e_phys_track_insert_failure;
   END;

   BEGIN
      FOR l_rec IN c_digital_tracks (v_max_revision) LOOP
         INSERT INTO pm_track_listing_digital
              VALUES l_rec;
      END LOOP;
   EXCEPTION
      WHEN OTHERS THEN
         ROLLBACK;
         v_error := SQLERRM;
         RAISE e_digi_track_insert_failure;
   END;

   BEGIN
      FOR l_rec IN c_promo_tracks (v_max_revision) LOOP
         INSERT INTO pm_track_listing_promo
              VALUES l_rec;
      END LOOP;
   EXCEPTION
      WHEN OTHERS THEN
         ROLLBACK;
         v_error := SQLERRM;
         RAISE e_promo_track_insert_failure;
   END;


   COMMIT;

   -- Reset IS BEING EDITED flag and editing user in PM_HEADER and PM_DRAFT_HEADER
   -- PM DRAFT HEADER is checked to see if a version is editable or not

   UPDATE pm_header
      SET is_being_edited = 'N'
    WHERE pm_ref_id = p_ref_id AND pm_revision_id = v_max_revision;


   UPDATE pm_draft_header
      SET is_being_edited = 'N'
    WHERE pm_ref_id = p_ref_id AND pm_revision_id = v_max_revision;


   COMMIT;




-- Send New or Edited Memo Email Notifications

   IF v_is_new_memo THEN
      -- Send new memo email notification
      project_memo_new_email (p_ref_id, v_max_revision);
   ELSE
      -- Log deleted products into pm_deleted_products table
      SELECT MAX(pm_revision_id) INTO v_prev_revision FROM pm_header WHERE pm_ref_id = p_ref_id AND pm_revision_id != v_max_revision;
      pm_deleted_products_log(p_ref_id, v_max_revision, v_prev_revision);
      -- Send revision notification email
      project_memo_edited_email (p_ref_id, v_max_revision);
   END IF;
EXCEPTION
   WHEN e_no_ref_id_header THEN
      INSERT INTO pm_save_exception
           VALUES (p_ref_id,
                   v_max_revision,
                   'Project Memo id or revision not found. PM_SAVE',
                   CURRENT_DATE,
                   'Y');

      COMMIT;
   WHEN e_physical_insert_failure THEN
      INSERT INTO pm_save_exception
           VALUES (p_ref_id,
                   v_max_revision,
                   'Error inserting in table PM_DETAIL_PHYSICAL.' || v_error,
                   CURRENT_DATE,
                   'Y');

      COMMIT;
   WHEN e_digital_insert_failure THEN
      INSERT INTO pm_save_exception
           VALUES (p_ref_id,
                   v_max_revision,
                   'Error inserting in table PM_DETAIL_DIGITAL.' || v_error,
                   CURRENT_DATE,
                   'Y');

      COMMIT;
   WHEN e_promos_insert_failure THEN
      INSERT INTO pm_save_exception
           VALUES (p_ref_id,
                   v_max_revision,
                   'Error inserting in table PM_DETAIL_PROMOS.' || v_error,
                   CURRENT_DATE,
                   'Y');

      COMMIT;
   WHEN e_phys_track_insert_failure THEN
      INSERT INTO pm_save_exception
           VALUES (p_ref_id,
                   v_max_revision,
                   'Error inserting in table PM_TRACK_LISTING_PHYSICAL.' || v_error,
                   CURRENT_DATE,
                   'Y');

      COMMIT;
   WHEN e_digi_track_insert_failure THEN
      INSERT INTO pm_save_exception
           VALUES (p_ref_id,
                   v_max_revision,
                   'Error inserting in table PM_TRACK_LISTING_DIGITAL.' || v_error,
                   CURRENT_DATE,
                   'Y');

      COMMIT;
   WHEN e_promo_track_insert_failure THEN
      INSERT INTO pm_save_exception
           VALUES (p_ref_id,
                   v_max_revision,
                   'Error inserting in table PM_TRACK_LISTING_PROMO.' || v_error,
                   CURRENT_DATE,
                   'Y');

      COMMIT;
   WHEN e_fatal_error THEN
      NULL;
   WHEN OTHERS THEN
      v_error := SQLERRM;

      INSERT INTO pm_save_exception
           VALUES (p_ref_id,
                   v_max_revision,
                   v_error,
                   CURRENT_DATE,
                   'Y');

      COMMIT;
      RAISE;
END project_memo_save1;
/

SHOW ERRORS;