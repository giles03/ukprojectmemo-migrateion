package com.sonybmg.struts.pmemo3.db;

import com.sonybmg.struts.pmemo3.model.ProjectMemo;
import com.sonybmg.struts.pmemo3.model.Track;
import com.sonybmg.struts.pmemo3.util.FormHelper;
import java.sql.*;



public class TrackDAO extends PMDAO {

            ProjectMemoDAO pmDAO;

            public TrackDAO() {
            	pmDAO = ProjectMemoFactoryDAO.getInstance();
            }

            public void insertDigitalTrack(Track t, ProjectMemo pm)  {
                
            	String sql;              
                sql = "";
                String trackName = "";
                FormHelper fh = new FormHelper();
				//ResultSet rs = null;
				Statement statement = null;
				Connection connection = null;
				if(t.getPreOrderOnlyFlag()==null){
					t.setPreOrderOnlyFlag("");
				}
                if (t.getTrackName() != null) {
                	trackName = fh.insertNullIfEmptyString(fh.replaceApostrophesInString(t.getTrackName()));
                }
                if (pmDAO.isProductInMobile(pm.getConfigurationId())) {
                	sql = "INSERT INTO PM_DRAFT_DIGITAL_TRACKS" +
                		  "(TRACK_NUM, " +
                		  "TRACK_NAME, " +
                		  "COMMENTS, " +
                		  "DSP_COMMENTS, " +
                		  "PM_REF_ID, " +
                		  "PM_REVISION_ID, " +
                		  "PM_DETAIL_ID, " +
                		  "ISRC_NUMBER, " +
                		  "TRACK_ORDER, " +
                		  "PRE_ORDER_ONLY, " +
                		  "MONIS_STATUS, " +
                		  "CSS_DIGITAL_ID, "+
                		  "MOBILE_GRID_NUMBER )" +
                          "VALUES("+t.getTrackNumber()+", "
                		  +trackName+", "
                          +fh.insertNullIfEmptyString(fh.replaceApostrophesInString(t.getComments()))+", "
                          +fh.insertNullIfEmptyString(fh.replaceApostrophesInString(t.getDspComments()))+", "
                          +pm.getMemoRef()+", "
                          +pm.getRevisionID()+", "
                          +pm.getDigitalDetailId()+", "
                          +fh.insertNullIfEmptyString(fh.replaceApostrophesInString(t.getIsrcNumber()))+", "
                          +t.getTrackOrder()+", '"
                          +t.getPreOrderOnlyFlag()+"', '"
                          +t.getMonisStatus()+"', '"
                          +t.getCssDigitalId()+"', "                                                  
                          +fh.insertNullIfEmptyString(fh.replaceApostrophesInString(t.getGridNumber()))+") ";
                } else {
                	sql = "INSERT INTO PM_DRAFT_DIGITAL_TRACKS" +
						  "(TRACK_NUM, " +
						  "TRACK_NAME, " +
						  "COMMENTS, " +
						  "DSP_COMMENTS, " +
						  "PM_REF_ID, " +
						  "PM_REVISION_ID, " +
						  "PM_DETAIL_ID, " +
						  "ISRC_NUMBER, " +
						  "TRACK_ORDER, " +
						  "PRE_ORDER_ONLY)" +
						  "VALUES("+t.getTrackNumber()+", "
						  +trackName+", "
						  +fh.insertNullIfEmptyString(fh.replaceApostrophesInString(t.getComments()))+", "
						  +fh.insertNullIfEmptyString(fh.replaceApostrophesInString(t.getDspComments()))+", "
						  +pm.getMemoRef()+", " 
						  +pm.getRevisionID()+", "
						  +pm.getDigitalDetailId()+", "
						  +fh.insertNullIfEmptyString(fh.replaceApostrophesInString(t.getIsrcNumber()))+", "
						  +t.getTrackOrder()+", '"
						  +t.getPreOrderOnlyFlag()+"')";						  
                }					
				try {
					connection = getConnection();
					statement = connection.createStatement();
					statement.execute(sql);
					//rs.next();
                } catch (SQLException e) {
					e.printStackTrace();				
				} finally {
				 	try {
				 		// rs.close();
				 		 statement.close();
		                 connection.close();
				 	} catch (SQLException e) {
				 		e.printStackTrace();
				 	}
				}
            }

            public void insertDETrackComment(Track t)  {
              
              String sql;              
              sql = "";
              String digiEquivComments = "";
              String digiEquivDSPComments = "";
              FormHelper fh = new FormHelper();
             //ResultSet rs = null;
              Statement statement = null;
              Connection connection = null;
              if(t.getPreOrderOnlyFlag()==null){
                  t.setPreOrderOnlyFlag("");
              }
              
              if (t.getDigiEquivComments() != null) {
                digiEquivComments = fh.insertNullIfEmptyString(fh.replaceApostrophesInString(t.getDigiEquivComments()));
              }   
              
              if (t.getDigiEquivDSPComments() != null) {
            	  digiEquivDSPComments = fh.insertNullIfEmptyString(fh.replaceApostrophesInString(t.getDigiEquivDSPComments()));
                }
                            
              sql = "UPDATE PM_DRAFT_PHYSICAL_TRACKS SET DE_COMMENTS ="+digiEquivComments+ 
            		", DSP_COMMENTS ="+digiEquivDSPComments+ 
                    " WHERE PM_REF_ID='"+t.getMemoRef()+
                    "' AND PM_REVISION_ID='"+t.getMemoRevisionId()+
                    "' AND PM_DETAIL_ID='"+t.getDetailId()+
                    "' AND TRACK_NUM='"+t.getTrackNumber()+"'";
                 
              try {
                  connection = getConnection();
                  statement = connection.createStatement();
                  statement.executeQuery(sql);
                  //rs.next();
              } catch (SQLException e) {
                  e.printStackTrace();                
              } finally {
                  try {
                       //rs.close();
                       statement.close();
                       connection.close();
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
              }
          }
            
            
            
            public void DeleteDigitalTracks(Track t, ProjectMemo pm)  {
                String sql;
				//ResultSet rs = null;
				Statement statement = null;
				Connection connection = null;
                sql = (new StringBuilder("DELETE FROM PM_DRAFT_DIGITAL_TRACKS(TRACK_ORDER, TRACK_NAME, COMMENTS, PM_REF_ID, PM_REVISION_ID, PM_DETAIL_ID )VALUES(")).append(t.getTrackOrder()).append(",").append("'").append(t.getTrackName()).append("', ").append("'").append(t.getComments()).append("', ").append(pm.getMemoRef()).append(",").append("1 ,").append(pm.getDigitalDetailId()).append(") ").toString();
				//rs = null;
					try {
						connection = getConnection();
						statement = connection.createStatement();
						statement.execute(sql);
						//rs.next();	
					} catch (SQLException e) {
						e.printStackTrace();						
					} finally {
					 	try {
					 		 //rs.close();
					 		 statement.close();
			                 connection.close();
					 	} catch (SQLException e) {
					 		e.printStackTrace();
					 	}
					}
            }

            
            
            
            
            public void insertPhysicalTrack(ProjectMemo pm, Track t)  {
            	String sql;
            	FormHelper fh = new FormHelper();
            	String trackName = "";
				//ResultSet rs = null;
				Statement statement = null;
				Connection connection = null;
            	if (t.getTrackName() != null) {
            		trackName = fh.replaceApostrophesInString(t.getTrackName());
            	}
            	sql = "INSERT INTO PM_DRAFT_PHYSICAL_TRACKS(TRACK_NUM, TRACK_NAME, COMMENTS, DE_COMMENTS, DSP_COMMENTS, PM_REF_ID, PM_REVISION_ID, PM_DETAIL_ID, ISRC_NUMBER, TRACK_ORDER) VALUES("+
            			t.getTrackNumber()+","+
            			fh.insertNullIfEmptyString(trackName)+","+
            			fh.insertNullIfEmptyString(fh.replaceApostrophesInString(t.getComments()))+","+
            			fh.insertNullIfEmptyString(fh.replaceApostrophesInString(t.getDigiEquivComments()))+", "+
            			fh.insertNullIfEmptyString(fh.replaceApostrophesInString(t.getDigiEquivDSPComments()))+", "+
            			pm.getMemoRef()+", "+
            			t.getMemoRevisionId()+", "+
            			pm.getPhysicalDetailId()+", "+
            			fh.insertNullIfEmptyString(fh.replaceApostrophesInString(t.getIsrcNumber()))+", "+
            			 t.getTrackOrder()+")";
            			          	
	            	try {
	            		connection = getConnection();
	            		statement = connection.createStatement();
	            		statement.execute(sql);
	            		//rs.next();
	            	} catch (SQLException e) {
	            		e.printStackTrace();
	            	} finally {
					 	try {
					 		 //rs.close();
					 		 statement.close();
			                 connection.close();
					 	} catch (SQLException e) {
					 		e.printStackTrace();
					 	}
	            	}
            }

           
  
            
            
            
         /*   public void insertPromoTrack(ProjectMemo pm, Track t) {
                String sql;                
                ResultSet rs = null;
                Statement statement = null;
                Connection connection = null;
                FormHelper fh = new FormHelper();
				String trackName = "";
					if (t.getTrackName() != null) {
						trackName = fh.replaceApostrophesInString(t.getTrackName());
					}
				sql = (new StringBuilder("INSERT INTO PM_DRAFT_PROMO_TRACKS(TRACK_NUM, TRACK_NAME, COMMENTS, PM_REF_ID, PM_REVISION_ID, PM_DETAIL_ID, ISRC_NUMBER, TRACK_ORDER )VALUES(")).append(t.getTrackNumber()).append(",").append("'").append(trackName).append("', ").append("'").append(fh.replaceApostrophesInString(t.getComments())).append("', ").append(pm.getMemoRef()).append(",").append(pm.getRevisionID()).append(",").append(pm.getPromoDetailId()).append(" ,'").append(fh.replaceApostrophesInString(t.getIsrcNumber())).append("',").append(t.getTrackOrder()).append(") ").toString();

					try {
						connection = getConnection();
						statement = connection.createStatement();
						rs = statement.executeQuery(sql);
						rs.next();
	
	                } catch (SQLException e) {
	                	e.printStackTrace();	                	
					} finally{						
					 	try {
					 		 rs.close();
					 		 statement.close();
			                 connection.close();
					 	} catch (SQLException e) {
					 		e.printStackTrace();
					 	}
					}
            }*/

            
            
            
            public boolean checkTracksExistForDigitalFormat(String memoRef, String revisionID, String detailId)  {
                boolean formatsExist;
                String sql;              
                ResultSet rs = null;
                PreparedStatement pstmt =null;
                Connection connection = null;
                formatsExist = false;
				sql = "SELECT * FROM PM_DRAFT_DIGITAL_TRACKS WHERE PM_REF_ID = ? AND PM_REVISION_ID = ? AND PM_DETAIL_ID = ? ";
				try {
					connection = getConnection();
					pstmt = connection.prepareStatement(sql);
					pstmt.setString(1, memoRef);
					pstmt.setString(2, revisionID);
					pstmt.setString(3, detailId);
					for (rs = pstmt.executeQuery(); rs.next();) {
					if (rs.getString(1) != null) {
						formatsExist = true;
                        }
                    }
                }  catch (SQLException e) {
                	e.printStackTrace();                	
				} finally{					
				 	try {
				 		 rs.close();
				 		 pstmt.close();
		                 connection.close();
				 	} catch (SQLException e) {
				 		e.printStackTrace();
				 	}	
				}            
				return formatsExist;
            }

            
            public void updatePhysicalDECommentsFromComments(String memoRef, String revisionID, String detailId)  {
              String sql;              
              PreparedStatement pstmt =null;
              Connection connection = null;

              sql = "UPDATE PM_DRAFT_PHYSICAL_TRACKS SET DE_COMMENTS = COMMENTS WHERE PM_REF_ID=? AND PM_REVISION_ID = ? AND PM_DETAIL_ID= ?";
              try {
                  connection = getConnection();
                  pstmt = connection.prepareStatement(sql);
                  pstmt.setString(1, memoRef);
                  pstmt.setString(2, revisionID);
                  pstmt.setString(3, detailId);
                  pstmt.execute(); 
              }  catch (SQLException e) {
                  e.printStackTrace();                    
              } finally{                  
                  try {
                       pstmt.close();
                       connection.close();
                  } catch (SQLException e) {
                       e.printStackTrace();
                  }   
              }            

          }
            
            
            
            public int getMaxTrackNumber(String memoRef, String detailId, String revisionID, String tableName)  {
                int maxTrackNum;
                String sql;
                ResultSet rs = null;
                Statement statement = null;
                Connection connection = null;
                maxTrackNum = 0;
				sql = (new StringBuilder("SELECT MAX(TRACK_NUM) FROM PM_DRAFT_")).append(tableName).append("_TRACKS ").append("WHERE PM_REF_ID =").append(memoRef).append(" ").append("AND PM_DETAIL_ID =").append(detailId).append(" ").toString();
				try {
					connection = getConnection();
					statement = connection.createStatement();					
					for (rs = statement.executeQuery(sql); rs.next();) {
						maxTrackNum = rs.getInt(1);
                    }
					rs.next();					
				} catch (SQLException e) {
						e.printStackTrace();				
				} finally {					
				 	try {
				 		 rs.close();
				 		 statement.close();
		                 connection.close();
				 	} catch (SQLException e) {
				 		e.printStackTrace();
				 	}		
				}
				return maxTrackNum;
            }

            
            
            
            
            
            public boolean checkTracksExistForPhysicalFormat(String memoRef, String revisionID, String detailId)  {
                boolean formatsExist;
                String sql;
                ResultSet rs = null;
                PreparedStatement pstmt =null;
                Connection connection = null;
                formatsExist = false;
				sql = "SELECT * FROM PM_DRAFT_PHYSICAL_TRACKS WHERE PM_REF_ID = ? AND PM_REVISION_ID = ? AND PM_DETAIL_ID = ? ";

				try {
					connection = getConnection();
					pstmt = connection.prepareStatement(sql);
					pstmt.setString(1, memoRef);
					pstmt.setString(2, revisionID);
					pstmt.setString(3, detailId);
					for (rs = pstmt.executeQuery(); rs.next();) {
					if (rs.getString(1) != null) {
						formatsExist = true;
                        }
                    }
				} catch (SQLException e) {
					e.printStackTrace();								
				} finally { 					
				 	try {
				 		 rs.close();
				 		 pstmt.close();
		                 connection.close();
				 	} catch (SQLException e) {
				 		e.printStackTrace();
				 	}
				}			
				return formatsExist;
            }

            
            
            
            
            
            public boolean checkTracksExistForPromoFormat(String memoRef, String revisionID, String detailId)  {
                boolean formatsExist;
                String sql;
                ResultSet rs = null;
                PreparedStatement pstmt =null;
                Connection connection = null;
                formatsExist = false;
				sql = "SELECT * FROM PM_DRAFT_PROMO_TRACKS WHERE PM_REF_ID = ? AND PM_REVISION_ID = ? AND PM_DETAIL_ID = ? ";
				rs = null;
				try {
					connection = getConnection();
					pstmt = connection.prepareStatement(sql);
					pstmt.setString(1, memoRef);
					pstmt.setString(2, revisionID);
					pstmt.setString(3, detailId);
						for (rs = pstmt.executeQuery(); rs.next();) {
							if (rs.getString(1) != null) {
									formatsExist = true;
		                        }
	                    }               
					} catch (SQLException e){ 
						e.printStackTrace();
            		} finally { 						
    				 	try {
   				 		 rs.close();
   				 		 pstmt.close();
   		                 connection.close();
   				 	} catch (SQLException e) {
   				 		e.printStackTrace();
   				 	}
					}						
					return formatsExist;
            }

            
            
            
            
            
            public boolean deleteTracksForPromoFormat(String memoRef, String revisionID, String detailId)  {
                boolean deleted;
                String sql;
                deleted = false;
				sql = (new StringBuilder("DELETE FROM PM_DRAFT_PROMO_TRACKS WHERE PM_REF_ID = ")).append(memoRef).append(" ").append("AND PM_REVISION_ID = ").append(revisionID).append(" ").append("AND PM_DETAIL_ID = ").append(detailId).toString();
				//ResultSet rs = null;
				Statement statement = null;
				Connection connection = null;
				try {
					connection = getConnection();
					statement = connection.createStatement();
					deleted= statement.execute(sql);
					//deleted = true;

				} catch (SQLException e) {
				    e.printStackTrace();				
				} finally{
				 	try {
				 		// rs.close();
				 		 statement.close();
		                 connection.close();
				 	} catch (SQLException e) {
				 		e.printStackTrace();
				 	}
				}
			return deleted;
            }

            
            
            
            public boolean deleteTracksForPhysicalFormat(String memoRef, String revisionID, String detailId)  {
                boolean deleted;
                String sql;
                deleted = false;
				sql = (new StringBuilder("DELETE FROM PM_DRAFT_PHYSICAL_TRACKS WHERE PM_REF_ID = ")).append(memoRef).append(" AND PM_REVISION_ID = ").append(revisionID).append(" AND PM_DETAIL_ID = ").append(detailId).toString();
				//ResultSet rs = null;
				Statement statement = null;
				Connection connection = null;
				try {
					connection = getConnection();
					statement = connection.createStatement();
					deleted = statement.execute(sql);
					//deleted = true;

				} catch (SQLException e) {
				    e.printStackTrace();					    
				} finally { 				
				 	try {
				 		// rs.close();
				 		 statement.close();
		                 connection.close();
				 	} catch (SQLException e) {
				 		e.printStackTrace();
				 	}
				}
				return deleted;
            }


            
            
            
            public boolean deleteTracksForDigitalFormat(String memoRef, String revisionID, String detailId) {
                boolean deleted;
                String sql;
               // ResultSet rs = null;
                Statement statement = null;
                Connection connection = null;
                deleted = false;
				sql = (new StringBuilder("DELETE FROM PM_DRAFT_DIGITAL_TRACKS WHERE PM_REF_ID = ")).append(memoRef).append(" AND PM_REVISION_ID = ").append(revisionID).append(" AND PM_DETAIL_ID = ").append(detailId).toString();
		
				try {
					connection = getConnection();
					statement = connection.createStatement();
					deleted = statement.execute(sql);
           			//deleted = true;
				} catch (Exception e) {
				    e.printStackTrace();				
				} finally{
				 	try {
				 		//rs.close();
				 		 statement.close();
		                 connection.close();
				 	} catch (Exception e) {
				 		e.printStackTrace();
				 	}
				}
				return deleted;
            }

            
            
            
            public boolean deleteTracksForPreOrders(String memoRef, String revisionID, String detailId)  {
                boolean deleted;
                String sql;
               //ResultSet rs = null;
                Statement statement = null;
                Connection connection = null;
                deleted = false;
				sql = (new StringBuilder("DELETE FROM PM_DRAFT_DIGITAL_TRACKS WHERE PM_REF_ID = ")).append(memoRef).append(" AND PRE_ORDER_ONLY='Y' AND PM_REVISION_ID = ").append(revisionID).append(" AND PM_DETAIL_ID = ").append(detailId).toString();
				try {
					connection = getConnection();
					statement = connection.createStatement();
					deleted  = statement.execute(sql);
					//deleted = true;
				} catch (SQLException e) {
				    e.printStackTrace();				
				}  finally { 				
				 	try {
				 		 //rs.close();
				 		 statement.close();
		                 connection.close();
				 	} catch (SQLException e) {
				 		e.printStackTrace();
				 	}
				}
			  return deleted;
            }

            
            
            
            protected Object wrapOne(ResultSet rs)  {
            	return null;
        
            }
}
