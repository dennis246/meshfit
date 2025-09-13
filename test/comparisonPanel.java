/* 
            // ck
            columnBlock += "<CK: {";
            {
                int entryIndex = 0;
                for (var entry : AddColumnsMap.entrySet()) {
                    var columnPropsMap = (LinkedHashMap<String, String>) entry.getValue();

                    if (columnPropsMap.get("iskey") != null) {

                        if (entryIndex > 0 && entryIndex <= AddColumnsMap.entrySet().size() - 1) {
                            columnBlock += ",\s";
                        }

                        columnBlock += entry.getKey();
                        entryIndex++;
                    }

                }

                columnBlock += "}>\r\n\r\n";
            }

            // autoinc
            columnBlock += "<CI: {";
            {
                int entryIndex = 0;
                for (var entry : AddColumnsMap.entrySet()) {
                    var columnPropsMap = (LinkedHashMap<String, String>) entry.getValue();

                    if (columnPropsMap.get("isautoincremental") != null) {

                        if (entryIndex > 0 && entryIndex <= AddColumnsMap.entrySet().size() - 1) {
                            columnBlock += ",\s";
                        }

                        columnBlock += entry.getKey() + "(" + columnPropsMap.get("incrementalfactor") + ")";

                    }

                    entryIndex++;
                }

                columnBlock += "}>\r\n\r\n";
            } */
