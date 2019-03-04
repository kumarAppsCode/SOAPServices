                  // passing value to body
                  String xmlData=ApprovalProcess.milstoneXMLData(p_AMOUNT, p_FUNC_ID, p_LEVEL_NO, p_PK_COLUMN, p_REF_ID, p_STATUS_COLUMN, p_TABLE_NAME, p_ATTRIBUTE1, p_ATTRIBUTE2);
                  System.err.println("Mile Stone Body==>"+xmlData);
                  String[] a=ApprovalProcess.invokeWsdl(xmlData, wsdl); 
                  System.out.println("0==>"+a[0]);
                  System.out.println("1==>"+a[1]);
                      if(a[0].equals("True")){
                        JSFUtils.addFacesInformationMessage("Milestone Submitted for Approval");
                      }
                    }      
--------------------------------------------------------------------------------------------
