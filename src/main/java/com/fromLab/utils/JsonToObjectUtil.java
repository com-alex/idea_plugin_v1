package com.fromLab.utils;


import com.fromLab.VO.TaskVO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jetbrains.uast.values.UBooleanConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: JIN KE
 * @Date: 2020/1/12 18:50
 */
public class JsonToObjectUtil {

    static String workpackage="{\n" +
            "    \"_type\": \"WorkPackage\",\n" +
            "    \"id\": 3,\n" +
            "    \"lockVersion\": 0,\n" +
            "    \"subject\": \"Development\",\n" +
            "    \"description\": {\n" +
            "        \"format\": \"textile\",\n" +
            "        \"raw\": null,\n" +
            "        \"html\": \"\"\n" +
            "    },\n" +
            "    \"startDate\": \"2020-01-17\",\n" +
            "    \"dueDate\": \"2020-02-05\",\n" +
            "    \"estimatedTime\": null,\n" +
            "    \"spentTime\": \"PT0S\",\n" +
            "    \"percentageDone\": 0,\n" +
            "    \"createdAt\": \"2020-01-07T11:53:06Z\",\n" +
            "    \"updatedAt\": \"2020-01-07T11:53:06Z\",\n" +
            "    \"laborCosts\": \"0.00 EUR\",\n" +
            "    \"materialCosts\": \"0.00 EUR\",\n" +
            "    \"overallCosts\": \"0.00 EUR\",\n" +
            "    \"remainingTime\": null,\n" +
            "    \"_embedded\": {\n" +
            "        \"watchers\": {\n" +
            "            \"_type\": \"Collection\",\n" +
            "            \"total\": 0,\n" +
            "            \"count\": 0,\n" +
            "            \"_embedded\": {\n" +
            "                \"elements\": []\n" +
            "            },\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/work_packages/3/watchers\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"attachments\": {\n" +
            "            \"_type\": \"Collection\",\n" +
            "            \"total\": 0,\n" +
            "            \"count\": 0,\n" +
            "            \"_embedded\": {\n" +
            "                \"elements\": []\n" +
            "            },\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/work_packages/3/attachments\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"relations\": {\n" +
            "            \"_type\": \"Collection\",\n" +
            "            \"total\": 3,\n" +
            "            \"count\": 3,\n" +
            "            \"_embedded\": {\n" +
            "                \"elements\": [\n" +
            "                    {\n" +
            "                        \"_type\": \"Relation\",\n" +
            "                        \"id\": 3,\n" +
            "                        \"name\": \"translation missing: en.label_\",\n" +
            "                        \"delay\": null,\n" +
            "                        \"description\": null,\n" +
            "                        \"_links\": {\n" +
            "                            \"self\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/3\"\n" +
            "                            },\n" +
            "                            \"updateImmediately\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/3\",\n" +
            "                                \"method\": \"patch\"\n" +
            "                            },\n" +
            "                            \"delete\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/3\",\n" +
            "                                \"method\": \"delete\",\n" +
            "                                \"title\": \"Remove relation\"\n" +
            "                            },\n" +
            "                            \"from\": {\n" +
            "                                \"href\": \"/openproject/api/v3/work_packages/3\",\n" +
            "                                \"title\": \"Development\"\n" +
            "                            },\n" +
            "                            \"to\": {\n" +
            "                                \"href\": \"/openproject/api/v3/work_packages/3\",\n" +
            "                                \"title\": \"Development\"\n" +
            "                            }\n" +
            "                        }\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"_type\": \"Relation\",\n" +
            "                        \"id\": 11,\n" +
            "                        \"name\": \"follows\",\n" +
            "                        \"type\": \"follows\",\n" +
            "                        \"reverseType\": \"precedes\",\n" +
            "                        \"delay\": null,\n" +
            "                        \"description\": null,\n" +
            "                        \"_links\": {\n" +
            "                            \"self\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/11\"\n" +
            "                            },\n" +
            "                            \"updateImmediately\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/11\",\n" +
            "                                \"method\": \"patch\"\n" +
            "                            },\n" +
            "                            \"delete\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/11\",\n" +
            "                                \"method\": \"delete\",\n" +
            "                                \"title\": \"Remove relation\"\n" +
            "                            },\n" +
            "                            \"from\": {\n" +
            "                                \"href\": \"/openproject/api/v3/work_packages/3\",\n" +
            "                                \"title\": \"Development\"\n" +
            "                            },\n" +
            "                            \"to\": {\n" +
            "                                \"href\": \"/openproject/api/v3/work_packages/2\",\n" +
            "                                \"title\": \"Project planning\"\n" +
            "                            }\n" +
            "                        }\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"_type\": \"Relation\",\n" +
            "                        \"id\": 18,\n" +
            "                        \"name\": \"follows\",\n" +
            "                        \"type\": \"follows\",\n" +
            "                        \"reverseType\": \"precedes\",\n" +
            "                        \"delay\": null,\n" +
            "                        \"description\": null,\n" +
            "                        \"_links\": {\n" +
            "                            \"self\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/18\"\n" +
            "                            },\n" +
            "                            \"updateImmediately\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/18\",\n" +
            "                                \"method\": \"patch\"\n" +
            "                            },\n" +
            "                            \"delete\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/18\",\n" +
            "                                \"method\": \"delete\",\n" +
            "                                \"title\": \"Remove relation\"\n" +
            "                            },\n" +
            "                            \"from\": {\n" +
            "                                \"href\": \"/openproject/api/v3/work_packages/7\",\n" +
            "                                \"title\": \"Go-Live\"\n" +
            "                            },\n" +
            "                            \"to\": {\n" +
            "                                \"href\": \"/openproject/api/v3/work_packages/3\",\n" +
            "                                \"title\": \"Development\"\n" +
            "                            }\n" +
            "                        }\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/work_packages/3/relations\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"type\": {\n" +
            "            \"_type\": \"Type\",\n" +
            "            \"id\": 3,\n" +
            "            \"name\": \"Phase\",\n" +
            "            \"color\": \"#06799F\",\n" +
            "            \"position\": 3,\n" +
            "            \"isDefault\": true,\n" +
            "            \"isMilestone\": false,\n" +
            "            \"createdAt\": \"2020-01-07T11:52:35Z\",\n" +
            "            \"updatedAt\": \"2020-01-07T11:52:35Z\",\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/types/3\",\n" +
            "                    \"title\": \"Phase\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"priority\": {\n" +
            "            \"_type\": \"Priority\",\n" +
            "            \"id\": 8,\n" +
            "            \"name\": \"Normal\",\n" +
            "            \"position\": 2,\n" +
            "            \"isDefault\": true,\n" +
            "            \"isActive\": true,\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/priorities/8\",\n" +
            "                    \"title\": \"Normal\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"project\": {\n" +
            "            \"_type\": \"Project\",\n" +
            "            \"id\": 1,\n" +
            "            \"identifier\": \"demo-project\",\n" +
            "            \"name\": \"Demo project\",\n" +
            "            \"description\": \"This is a description for your project. You can edit the description in the Project settings -> Description\",\n" +
            "            \"createdAt\": \"2020-01-07T11:53:05Z\",\n" +
            "            \"updatedAt\": \"2020-01-07T11:53:05Z\",\n" +
            "            \"type\": null,\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/projects/1\",\n" +
            "                    \"title\": \"Demo project\"\n" +
            "                },\n" +
            "                \"createWorkPackage\": {\n" +
            "                    \"href\": \"/openproject/api/v3/projects/1/work_packages/form\",\n" +
            "                    \"method\": \"post\"\n" +
            "                },\n" +
            "                \"createWorkPackageImmediate\": {\n" +
            "                    \"href\": \"/openproject/api/v3/projects/1/work_packages\",\n" +
            "                    \"method\": \"post\"\n" +
            "                },\n" +
            "                \"categories\": {\n" +
            "                    \"href\": \"/openproject/api/v3/projects/1/categories\"\n" +
            "                },\n" +
            "                \"versions\": {\n" +
            "                    \"href\": \"/openproject/api/v3/projects/1/versions\"\n" +
            "                },\n" +
            "                \"types\": {\n" +
            "                    \"href\": \"/openproject/api/v3/projects/1/types\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"status\": {\n" +
            "            \"_type\": \"Status\",\n" +
            "            \"id\": 6,\n" +
            "            \"name\": \"Scheduled\",\n" +
            "            \"isClosed\": false,\n" +
            "            \"isDefault\": false,\n" +
            "            \"defaultDoneRatio\": null,\n" +
            "            \"position\": 6,\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/statuses/6\",\n" +
            "                    \"title\": \"Scheduled\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"author\": {\n" +
            "            \"_type\": \"User\",\n" +
            "            \"id\": 1,\n" +
            "            \"login\": \"admin\",\n" +
            "            \"admin\": true,\n" +
            "            \"subtype\": \"User\",\n" +
            "            \"firstName\": \"OpenProject\",\n" +
            "            \"lastName\": \"Admin\",\n" +
            "            \"name\": \"OpenProject Admin\",\n" +
            "            \"email\": null,\n" +
            "            \"avatar\": \"http://gravatar.com/avatar/cb4f282fed12016bd18a879c1f27ff97?secure=false\",\n" +
            "            \"createdAt\": \"2020-01-07T11:53:04Z\",\n" +
            "            \"updatedAt\": \"2020-01-10T04:05:56Z\",\n" +
            "            \"status\": \"active\",\n" +
            "            \"identityUrl\": null,\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/users/1\",\n" +
            "                    \"title\": \"OpenProject Admin\"\n" +
            "                },\n" +
            "                \"showUser\": {\n" +
            "                    \"href\": \"/openproject/users/1\",\n" +
            "                    \"type\": \"text/html\"\n" +
            "                },\n" +
            "                \"updateImmediately\": {\n" +
            "                    \"href\": \"/openproject/api/v3/users/1\",\n" +
            "                    \"title\": \"Update admin\",\n" +
            "                    \"method\": \"patch\"\n" +
            "                },\n" +
            "                \"lock\": {\n" +
            "                    \"href\": \"/openproject/api/v3/users/1/lock\",\n" +
            "                    \"title\": \"Set lock on admin\",\n" +
            "                    \"method\": \"post\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"assignee\": {\n" +
            "            \"_type\": \"User\",\n" +
            "            \"id\": 1,\n" +
            "            \"login\": \"admin\",\n" +
            "            \"admin\": true,\n" +
            "            \"subtype\": \"User\",\n" +
            "            \"firstName\": \"OpenProject\",\n" +
            "            \"lastName\": \"Admin\",\n" +
            "            \"name\": \"OpenProject Admin\",\n" +
            "            \"email\": null,\n" +
            "            \"avatar\": \"http://gravatar.com/avatar/cb4f282fed12016bd18a879c1f27ff97?secure=false\",\n" +
            "            \"createdAt\": \"2020-01-07T11:53:04Z\",\n" +
            "            \"updatedAt\": \"2020-01-10T04:05:56Z\",\n" +
            "            \"status\": \"active\",\n" +
            "            \"identityUrl\": null,\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/users/1\",\n" +
            "                    \"title\": \"OpenProject Admin\"\n" +
            "                },\n" +
            "                \"showUser\": {\n" +
            "                    \"href\": \"/openproject/users/1\",\n" +
            "                    \"type\": \"text/html\"\n" +
            "                },\n" +
            "                \"updateImmediately\": {\n" +
            "                    \"href\": \"/openproject/api/v3/users/1\",\n" +
            "                    \"title\": \"Update admin\",\n" +
            "                    \"method\": \"patch\"\n" +
            "                },\n" +
            "                \"lock\": {\n" +
            "                    \"href\": \"/openproject/api/v3/users/1/lock\",\n" +
            "                    \"title\": \"Set lock on admin\",\n" +
            "                    \"method\": \"post\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"costsByType\": {\n" +
            "            \"_type\": \"Collection\",\n" +
            "            \"total\": 0,\n" +
            "            \"count\": 0,\n" +
            "            \"_embedded\": {\n" +
            "                \"elements\": []\n" +
            "            },\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/work_packages/3/summarized_costs_by_type\"\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    },\n" +
            "    \"_links\": {\n" +
            "        \"self\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3\",\n" +
            "            \"title\": \"Development\"\n" +
            "        },\n" +
            "        \"update\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3/form\",\n" +
            "            \"method\": \"post\"\n" +
            "        },\n" +
            "        \"schema\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/schemas/1-3\"\n" +
            "        },\n" +
            "        \"updateImmediately\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3\",\n" +
            "            \"method\": \"patch\"\n" +
            "        },\n" +
            "        \"delete\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3\",\n" +
            "            \"method\": \"delete\"\n" +
            "        },\n" +
            "        \"logTime\": {\n" +
            "            \"href\": \"/openproject/work_packages/3/time_entries/new\",\n" +
            "            \"type\": \"text/html\",\n" +
            "            \"title\": \"Log time on Development\"\n" +
            "        },\n" +
            "        \"move\": {\n" +
            "            \"href\": \"/openproject/work_packages/3/move/new\",\n" +
            "            \"type\": \"text/html\",\n" +
            "            \"title\": \"Move Development\"\n" +
            "        },\n" +
            "        \"copy\": {\n" +
            "            \"href\": \"/openproject/work_packages/3/move/new?copy=true&ids%5B%5D=3\",\n" +
            "            \"type\": \"text/html\",\n" +
            "            \"title\": \"Copy Development\"\n" +
            "        },\n" +
            "        \"pdf\": {\n" +
            "            \"href\": \"/openproject/work_packages/3.pdf\",\n" +
            "            \"type\": \"application/pdf\",\n" +
            "            \"title\": \"Export as PDF\"\n" +
            "        },\n" +
            "        \"atom\": {\n" +
            "            \"href\": \"/openproject/work_packages/3.atom\",\n" +
            "            \"type\": \"application/rss+xml\",\n" +
            "            \"title\": \"Atom feed\"\n" +
            "        },\n" +
            "        \"available_relation_candidates\": {\n" +
            "            \"href\": \"/api/v3/work_packages/3/available_relation_candidates\",\n" +
            "            \"title\": \"Potential work packages to relate to\"\n" +
            "        },\n" +
            "        \"customFields\": {\n" +
            "            \"href\": \"/openproject/projects/demo-project/settings/custom_fields\",\n" +
            "            \"type\": \"text/html\",\n" +
            "            \"title\": \"Custom fields\"\n" +
            "        },\n" +
            "        \"configureForm\": {\n" +
            "            \"href\": \"/openproject/types/3/edit?tab=form_configuration\",\n" +
            "            \"type\": \"text/html\",\n" +
            "            \"title\": \"Configure form\"\n" +
            "        },\n" +
            "        \"activities\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3/activities\"\n" +
            "        },\n" +
            "        \"attachments\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3/attachments\"\n" +
            "        },\n" +
            "        \"addAttachment\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3/attachments\",\n" +
            "            \"method\": \"post\"\n" +
            "        },\n" +
            "        \"availableWatchers\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3/available_watchers\"\n" +
            "        },\n" +
            "        \"relations\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3/relations\"\n" +
            "        },\n" +
            "        \"revisions\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3/revisions\"\n" +
            "        },\n" +
            "        \"watch\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3/watchers\",\n" +
            "            \"method\": \"post\",\n" +
            "            \"payload\": {\n" +
            "                \"user\": {\n" +
            "                    \"href\": \"/openproject/api/v3/users/1\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"watchers\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3/watchers\"\n" +
            "        },\n" +
            "        \"addWatcher\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3/watchers\",\n" +
            "            \"method\": \"post\",\n" +
            "            \"payload\": {\n" +
            "                \"user\": {\n" +
            "                    \"href\": \"/openproject/api/v3/users/{user_id}\"\n" +
            "                }\n" +
            "            },\n" +
            "            \"templated\": true\n" +
            "        },\n" +
            "        \"removeWatcher\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3/watchers/{user_id}\",\n" +
            "            \"method\": \"delete\",\n" +
            "            \"templated\": true\n" +
            "        },\n" +
            "        \"addRelation\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3/relations\",\n" +
            "            \"method\": \"post\",\n" +
            "            \"title\": \"Add relation\"\n" +
            "        },\n" +
            "        \"addChild\": {\n" +
            "            \"href\": \"/openproject/api/v3/projects/demo-project/work_packages\",\n" +
            "            \"method\": \"post\",\n" +
            "            \"title\": \"Add child of Development\"\n" +
            "        },\n" +
            "        \"changeParent\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3\",\n" +
            "            \"method\": \"patch\",\n" +
            "            \"title\": \"Change parent of Development\"\n" +
            "        },\n" +
            "        \"addComment\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3/activities\",\n" +
            "            \"method\": \"post\",\n" +
            "            \"title\": \"Add comment\"\n" +
            "        },\n" +
            "        \"previewMarkup\": {\n" +
            "            \"href\": \"/openproject/api/v3/render/textile?context=/openproject/api/v3/work_packages/3\",\n" +
            "            \"method\": \"post\"\n" +
            "        },\n" +
            "        \"timeEntries\": {\n" +
            "            \"href\": \"/openproject/work_packages/3/time_entries\",\n" +
            "            \"type\": \"text/html\",\n" +
            "            \"title\": \"Time entries\"\n" +
            "        },\n" +
            "        \"children\": [\n" +
            "            {\n" +
            "                \"href\": \"/openproject/api/v3/work_packages/4\",\n" +
            "                \"title\": \"Great feature\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"href\": \"/openproject/api/v3/work_packages/5\",\n" +
            "                \"title\": \"Best feature\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"href\": \"/openproject/api/v3/work_packages/6\",\n" +
            "                \"title\": \"Terrible bug\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"ancestors\": [],\n" +
            "        \"category\": {\n" +
            "            \"href\": null\n" +
            "        },\n" +
            "        \"type\": {\n" +
            "            \"href\": \"/openproject/api/v3/types/3\",\n" +
            "            \"title\": \"Phase\"\n" +
            "        },\n" +
            "        \"priority\": {\n" +
            "            \"href\": \"/openproject/api/v3/priorities/8\",\n" +
            "            \"title\": \"Normal\"\n" +
            "        },\n" +
            "        \"project\": {\n" +
            "            \"href\": \"/openproject/api/v3/projects/1\",\n" +
            "            \"title\": \"Demo project\"\n" +
            "        },\n" +
            "        \"status\": {\n" +
            "            \"href\": \"/openproject/api/v3/statuses/6\",\n" +
            "            \"title\": \"Scheduled\"\n" +
            "        },\n" +
            "        \"author\": {\n" +
            "            \"href\": \"/openproject/api/v3/users/1\",\n" +
            "            \"title\": \"OpenProject Admin\"\n" +
            "        },\n" +
            "        \"responsible\": {\n" +
            "            \"href\": null\n" +
            "        },\n" +
            "        \"assignee\": {\n" +
            "            \"href\": \"/openproject/api/v3/users/1\",\n" +
            "            \"title\": \"OpenProject Admin\"\n" +
            "        },\n" +
            "        \"version\": {\n" +
            "            \"href\": null\n" +
            "        },\n" +
            "        \"parent\": {\n" +
            "            \"href\": null,\n" +
            "            \"title\": null\n" +
            "        },\n" +
            "        \"logCosts\": {\n" +
            "            \"href\": \"/openproject/work_packages/3/cost_entries/new\",\n" +
            "            \"type\": \"text/html\",\n" +
            "            \"title\": \"Log costs on Development\"\n" +
            "        },\n" +
            "        \"showCosts\": {\n" +
            "            \"href\": \"/openproject/work_packages/3/cost_entries\",\n" +
            "            \"type\": \"text/html\",\n" +
            "            \"title\": \"Show cost entries\"\n" +
            "        },\n" +
            "        \"costObject\": {\n" +
            "            \"href\": null\n" +
            "        },\n" +
            "        \"costsByType\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/3/summarized_costs_by_type\"\n" +
            "        }\n" +
            "    }\n" +
            "}";
    static String milestones="{\n" +
            "    \"_type\": \"WorkPackage\",\n" +
            "    \"id\": 7,\n" +
            "    \"lockVersion\": 1,\n" +
            "    \"subject\": \"Go-Live\",\n" +
            "    \"description\": {\n" +
            "        \"format\": \"textile\",\n" +
            "        \"raw\": null,\n" +
            "        \"html\": \"\"\n" +
            "    },\n" +
            "    \"date\": \"2020-02-06\",\n" +
            "    \"estimatedTime\": null,\n" +
            "    \"spentTime\": \"PT3H\",\n" +
            "    \"percentageDone\": 15,\n" +
            "    \"createdAt\": \"2020-01-07T11:53:06Z\",\n" +
            "    \"updatedAt\": \"2020-01-13T01:56:08Z\",\n" +
            "    \"laborCosts\": \"0.00 EUR\",\n" +
            "    \"materialCosts\": \"0.00 EUR\",\n" +
            "    \"overallCosts\": \"0.00 EUR\",\n" +
            "    \"remainingTime\": null,\n" +
            "    \"_embedded\": {\n" +
            "        \"watchers\": {\n" +
            "            \"_type\": \"Collection\",\n" +
            "            \"total\": 0,\n" +
            "            \"count\": 0,\n" +
            "            \"_embedded\": {\n" +
            "                \"elements\": []\n" +
            "            },\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/work_packages/7/watchers\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"attachments\": {\n" +
            "            \"_type\": \"Collection\",\n" +
            "            \"total\": 0,\n" +
            "            \"count\": 0,\n" +
            "            \"_embedded\": {\n" +
            "                \"elements\": []\n" +
            "            },\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/work_packages/7/attachments\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"relations\": {\n" +
            "            \"_type\": \"Collection\",\n" +
            "            \"total\": 3,\n" +
            "            \"count\": 3,\n" +
            "            \"_embedded\": {\n" +
            "                \"elements\": [\n" +
            "                    {\n" +
            "                        \"_type\": \"Relation\",\n" +
            "                        \"id\": 10,\n" +
            "                        \"name\": \"translation missing: en.label_\",\n" +
            "                        \"delay\": null,\n" +
            "                        \"description\": null,\n" +
            "                        \"_links\": {\n" +
            "                            \"self\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/10\"\n" +
            "                            },\n" +
            "                            \"updateImmediately\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/10\",\n" +
            "                                \"method\": \"patch\"\n" +
            "                            },\n" +
            "                            \"delete\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/10\",\n" +
            "                                \"method\": \"delete\",\n" +
            "                                \"title\": \"Remove relation\"\n" +
            "                            },\n" +
            "                            \"from\": {\n" +
            "                                \"href\": \"/openproject/api/v3/work_packages/7\",\n" +
            "                                \"title\": \"Go-Live\"\n" +
            "                            },\n" +
            "                            \"to\": {\n" +
            "                                \"href\": \"/openproject/api/v3/work_packages/7\",\n" +
            "                                \"title\": \"Go-Live\"\n" +
            "                            }\n" +
            "                        }\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"_type\": \"Relation\",\n" +
            "                        \"id\": 18,\n" +
            "                        \"name\": \"follows\",\n" +
            "                        \"type\": \"follows\",\n" +
            "                        \"reverseType\": \"precedes\",\n" +
            "                        \"delay\": null,\n" +
            "                        \"description\": null,\n" +
            "                        \"_links\": {\n" +
            "                            \"self\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/18\"\n" +
            "                            },\n" +
            "                            \"updateImmediately\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/18\",\n" +
            "                                \"method\": \"patch\"\n" +
            "                            },\n" +
            "                            \"delete\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/18\",\n" +
            "                                \"method\": \"delete\",\n" +
            "                                \"title\": \"Remove relation\"\n" +
            "                            },\n" +
            "                            \"from\": {\n" +
            "                                \"href\": \"/openproject/api/v3/work_packages/7\",\n" +
            "                                \"title\": \"Go-Live\"\n" +
            "                            },\n" +
            "                            \"to\": {\n" +
            "                                \"href\": \"/openproject/api/v3/work_packages/3\",\n" +
            "                                \"title\": \"Development\"\n" +
            "                            }\n" +
            "                        }\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"_type\": \"Relation\",\n" +
            "                        \"id\": 19,\n" +
            "                        \"name\": \"follows\",\n" +
            "                        \"type\": \"follows\",\n" +
            "                        \"reverseType\": \"precedes\",\n" +
            "                        \"delay\": null,\n" +
            "                        \"description\": null,\n" +
            "                        \"_links\": {\n" +
            "                            \"self\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/19\"\n" +
            "                            },\n" +
            "                            \"updateImmediately\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/19\",\n" +
            "                                \"method\": \"patch\"\n" +
            "                            },\n" +
            "                            \"delete\": {\n" +
            "                                \"href\": \"/openproject/api/v3/relations/19\",\n" +
            "                                \"method\": \"delete\",\n" +
            "                                \"title\": \"Remove relation\"\n" +
            "                            },\n" +
            "                            \"from\": {\n" +
            "                                \"href\": \"/openproject/api/v3/work_packages/7\",\n" +
            "                                \"title\": \"Go-Live\"\n" +
            "                            },\n" +
            "                            \"to\": {\n" +
            "                                \"href\": \"/openproject/api/v3/work_packages/2\",\n" +
            "                                \"title\": \"Project planning\"\n" +
            "                            }\n" +
            "                        }\n" +
            "                    }\n" +
            "                ]\n" +
            "            },\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/work_packages/7/relations\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"type\": {\n" +
            "            \"_type\": \"Type\",\n" +
            "            \"id\": 2,\n" +
            "            \"name\": \"Milestone\",\n" +
            "            \"color\": \"#35C53F\",\n" +
            "            \"position\": 2,\n" +
            "            \"isDefault\": true,\n" +
            "            \"isMilestone\": true,\n" +
            "            \"createdAt\": \"2020-01-07T11:52:35Z\",\n" +
            "            \"updatedAt\": \"2020-01-07T11:52:35Z\",\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/types/2\",\n" +
            "                    \"title\": \"Milestone\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"priority\": {\n" +
            "            \"_type\": \"Priority\",\n" +
            "            \"id\": 8,\n" +
            "            \"name\": \"Normal\",\n" +
            "            \"position\": 2,\n" +
            "            \"isDefault\": true,\n" +
            "            \"isActive\": true,\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/priorities/8\",\n" +
            "                    \"title\": \"Normal\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"project\": {\n" +
            "            \"_type\": \"Project\",\n" +
            "            \"id\": 1,\n" +
            "            \"identifier\": \"demo-project\",\n" +
            "            \"name\": \"Demo project\",\n" +
            "            \"description\": \"This is a description for your project. You can edit the description in the Project settings -> Description\",\n" +
            "            \"createdAt\": \"2020-01-07T11:53:05Z\",\n" +
            "            \"updatedAt\": \"2020-01-07T11:53:05Z\",\n" +
            "            \"type\": null,\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/projects/1\",\n" +
            "                    \"title\": \"Demo project\"\n" +
            "                },\n" +
            "                \"createWorkPackage\": {\n" +
            "                    \"href\": \"/openproject/api/v3/projects/1/work_packages/form\",\n" +
            "                    \"method\": \"post\"\n" +
            "                },\n" +
            "                \"createWorkPackageImmediate\": {\n" +
            "                    \"href\": \"/openproject/api/v3/projects/1/work_packages\",\n" +
            "                    \"method\": \"post\"\n" +
            "                },\n" +
            "                \"categories\": {\n" +
            "                    \"href\": \"/openproject/api/v3/projects/1/categories\"\n" +
            "                },\n" +
            "                \"versions\": {\n" +
            "                    \"href\": \"/openproject/api/v3/projects/1/versions\"\n" +
            "                },\n" +
            "                \"types\": {\n" +
            "                    \"href\": \"/openproject/api/v3/projects/1/types\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"status\": {\n" +
            "            \"_type\": \"Status\",\n" +
            "            \"id\": 6,\n" +
            "            \"name\": \"Scheduled\",\n" +
            "            \"isClosed\": false,\n" +
            "            \"isDefault\": false,\n" +
            "            \"defaultDoneRatio\": null,\n" +
            "            \"position\": 6,\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/statuses/6\",\n" +
            "                    \"title\": \"Scheduled\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"author\": {\n" +
            "            \"_type\": \"User\",\n" +
            "            \"id\": 1,\n" +
            "            \"login\": \"admin\",\n" +
            "            \"admin\": true,\n" +
            "            \"subtype\": \"User\",\n" +
            "            \"firstName\": \"OpenProject\",\n" +
            "            \"lastName\": \"Admin\",\n" +
            "            \"name\": \"OpenProject Admin\",\n" +
            "            \"email\": null,\n" +
            "            \"avatar\": \"http://gravatar.com/avatar/cb4f282fed12016bd18a879c1f27ff97?secure=false\",\n" +
            "            \"createdAt\": \"2020-01-07T11:53:04Z\",\n" +
            "            \"updatedAt\": \"2020-01-13T01:33:43Z\",\n" +
            "            \"status\": \"active\",\n" +
            "            \"identityUrl\": null,\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/users/1\",\n" +
            "                    \"title\": \"OpenProject Admin\"\n" +
            "                },\n" +
            "                \"showUser\": {\n" +
            "                    \"href\": \"/openproject/users/1\",\n" +
            "                    \"type\": \"text/html\"\n" +
            "                },\n" +
            "                \"updateImmediately\": {\n" +
            "                    \"href\": \"/openproject/api/v3/users/1\",\n" +
            "                    \"title\": \"Update admin\",\n" +
            "                    \"method\": \"patch\"\n" +
            "                },\n" +
            "                \"lock\": {\n" +
            "                    \"href\": \"/openproject/api/v3/users/1/lock\",\n" +
            "                    \"title\": \"Set lock on admin\",\n" +
            "                    \"method\": \"post\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"assignee\": {\n" +
            "            \"_type\": \"User\",\n" +
            "            \"id\": 1,\n" +
            "            \"login\": \"admin\",\n" +
            "            \"admin\": true,\n" +
            "            \"subtype\": \"User\",\n" +
            "            \"firstName\": \"OpenProject\",\n" +
            "            \"lastName\": \"Admin\",\n" +
            "            \"name\": \"OpenProject Admin\",\n" +
            "            \"email\": null,\n" +
            "            \"avatar\": \"http://gravatar.com/avatar/cb4f282fed12016bd18a879c1f27ff97?secure=false\",\n" +
            "            \"createdAt\": \"2020-01-07T11:53:04Z\",\n" +
            "            \"updatedAt\": \"2020-01-13T01:33:43Z\",\n" +
            "            \"status\": \"active\",\n" +
            "            \"identityUrl\": null,\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/users/1\",\n" +
            "                    \"title\": \"OpenProject Admin\"\n" +
            "                },\n" +
            "                \"showUser\": {\n" +
            "                    \"href\": \"/openproject/users/1\",\n" +
            "                    \"type\": \"text/html\"\n" +
            "                },\n" +
            "                \"updateImmediately\": {\n" +
            "                    \"href\": \"/openproject/api/v3/users/1\",\n" +
            "                    \"title\": \"Update admin\",\n" +
            "                    \"method\": \"patch\"\n" +
            "                },\n" +
            "                \"lock\": {\n" +
            "                    \"href\": \"/openproject/api/v3/users/1/lock\",\n" +
            "                    \"title\": \"Set lock on admin\",\n" +
            "                    \"method\": \"post\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"costsByType\": {\n" +
            "            \"_type\": \"Collection\",\n" +
            "            \"total\": 0,\n" +
            "            \"count\": 0,\n" +
            "            \"_embedded\": {\n" +
            "                \"elements\": []\n" +
            "            },\n" +
            "            \"_links\": {\n" +
            "                \"self\": {\n" +
            "                    \"href\": \"/openproject/api/v3/work_packages/7/summarized_costs_by_type\"\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    },\n" +
            "    \"_links\": {\n" +
            "        \"self\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7\",\n" +
            "            \"title\": \"Go-Live\"\n" +
            "        },\n" +
            "        \"update\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7/form\",\n" +
            "            \"method\": \"post\"\n" +
            "        },\n" +
            "        \"schema\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/schemas/1-2\"\n" +
            "        },\n" +
            "        \"updateImmediately\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7\",\n" +
            "            \"method\": \"patch\"\n" +
            "        },\n" +
            "        \"delete\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7\",\n" +
            "            \"method\": \"delete\"\n" +
            "        },\n" +
            "        \"logTime\": {\n" +
            "            \"href\": \"/openproject/work_packages/7/time_entries/new\",\n" +
            "            \"type\": \"text/html\",\n" +
            "            \"title\": \"Log time on Go-Live\"\n" +
            "        },\n" +
            "        \"move\": {\n" +
            "            \"href\": \"/openproject/work_packages/7/move/new\",\n" +
            "            \"type\": \"text/html\",\n" +
            "            \"title\": \"Move Go-Live\"\n" +
            "        },\n" +
            "        \"copy\": {\n" +
            "            \"href\": \"/openproject/work_packages/7/move/new?copy=true&ids%5B%5D=7\",\n" +
            "            \"type\": \"text/html\",\n" +
            "            \"title\": \"Copy Go-Live\"\n" +
            "        },\n" +
            "        \"pdf\": {\n" +
            "            \"href\": \"/openproject/work_packages/7.pdf\",\n" +
            "            \"type\": \"application/pdf\",\n" +
            "            \"title\": \"Export as PDF\"\n" +
            "        },\n" +
            "        \"atom\": {\n" +
            "            \"href\": \"/openproject/work_packages/7.atom\",\n" +
            "            \"type\": \"application/rss+xml\",\n" +
            "            \"title\": \"Atom feed\"\n" +
            "        },\n" +
            "        \"available_relation_candidates\": {\n" +
            "            \"href\": \"/api/v3/work_packages/7/available_relation_candidates\",\n" +
            "            \"title\": \"Potential work packages to relate to\"\n" +
            "        },\n" +
            "        \"customFields\": {\n" +
            "            \"href\": \"/openproject/projects/demo-project/settings/custom_fields\",\n" +
            "            \"type\": \"text/html\",\n" +
            "            \"title\": \"Custom fields\"\n" +
            "        },\n" +
            "        \"configureForm\": {\n" +
            "            \"href\": \"/openproject/types/2/edit?tab=form_configuration\",\n" +
            "            \"type\": \"text/html\",\n" +
            "            \"title\": \"Configure form\"\n" +
            "        },\n" +
            "        \"activities\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7/activities\"\n" +
            "        },\n" +
            "        \"attachments\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7/attachments\"\n" +
            "        },\n" +
            "        \"addAttachment\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7/attachments\",\n" +
            "            \"method\": \"post\"\n" +
            "        },\n" +
            "        \"availableWatchers\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7/available_watchers\"\n" +
            "        },\n" +
            "        \"relations\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7/relations\"\n" +
            "        },\n" +
            "        \"revisions\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7/revisions\"\n" +
            "        },\n" +
            "        \"watch\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7/watchers\",\n" +
            "            \"method\": \"post\",\n" +
            "            \"payload\": {\n" +
            "                \"user\": {\n" +
            "                    \"href\": \"/openproject/api/v3/users/1\"\n" +
            "                }\n" +
            "            }\n" +
            "        },\n" +
            "        \"watchers\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7/watchers\"\n" +
            "        },\n" +
            "        \"addWatcher\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7/watchers\",\n" +
            "            \"method\": \"post\",\n" +
            "            \"payload\": {\n" +
            "                \"user\": {\n" +
            "                    \"href\": \"/openproject/api/v3/users/{user_id}\"\n" +
            "                }\n" +
            "            },\n" +
            "            \"templated\": true\n" +
            "        },\n" +
            "        \"removeWatcher\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7/watchers/{user_id}\",\n" +
            "            \"method\": \"delete\",\n" +
            "            \"templated\": true\n" +
            "        },\n" +
            "        \"addRelation\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7/relations\",\n" +
            "            \"method\": \"post\",\n" +
            "            \"title\": \"Add relation\"\n" +
            "        },\n" +
            "        \"addChild\": {\n" +
            "            \"href\": \"/openproject/api/v3/projects/demo-project/work_packages\",\n" +
            "            \"method\": \"post\",\n" +
            "            \"title\": \"Add child of Go-Live\"\n" +
            "        },\n" +
            "        \"changeParent\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7\",\n" +
            "            \"method\": \"patch\",\n" +
            "            \"title\": \"Change parent of Go-Live\"\n" +
            "        },\n" +
            "        \"addComment\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7/activities\",\n" +
            "            \"method\": \"post\",\n" +
            "            \"title\": \"Add comment\"\n" +
            "        },\n" +
            "        \"previewMarkup\": {\n" +
            "            \"href\": \"/openproject/api/v3/render/textile?context=/openproject/api/v3/work_packages/7\",\n" +
            "            \"method\": \"post\"\n" +
            "        },\n" +
            "        \"timeEntries\": {\n" +
            "            \"href\": \"/openproject/work_packages/7/time_entries\",\n" +
            "            \"type\": \"text/html\",\n" +
            "            \"title\": \"Time entries\"\n" +
            "        },\n" +
            "        \"ancestors\": [],\n" +
            "        \"category\": {\n" +
            "            \"href\": null\n" +
            "        },\n" +
            "        \"type\": {\n" +
            "            \"href\": \"/openproject/api/v3/types/2\",\n" +
            "            \"title\": \"Milestone\"\n" +
            "        },\n" +
            "        \"priority\": {\n" +
            "            \"href\": \"/openproject/api/v3/priorities/8\",\n" +
            "            \"title\": \"Normal\"\n" +
            "        },\n" +
            "        \"project\": {\n" +
            "            \"href\": \"/openproject/api/v3/projects/1\",\n" +
            "            \"title\": \"Demo project\"\n" +
            "        },\n" +
            "        \"status\": {\n" +
            "            \"href\": \"/openproject/api/v3/statuses/6\",\n" +
            "            \"title\": \"Scheduled\"\n" +
            "        },\n" +
            "        \"author\": {\n" +
            "            \"href\": \"/openproject/api/v3/users/1\",\n" +
            "            \"title\": \"OpenProject Admin\"\n" +
            "        },\n" +
            "        \"responsible\": {\n" +
            "            \"href\": null\n" +
            "        },\n" +
            "        \"assignee\": {\n" +
            "            \"href\": \"/openproject/api/v3/users/1\",\n" +
            "            \"title\": \"OpenProject Admin\"\n" +
            "        },\n" +
            "        \"version\": {\n" +
            "            \"href\": null\n" +
            "        },\n" +
            "        \"parent\": {\n" +
            "            \"href\": null,\n" +
            "            \"title\": null\n" +
            "        },\n" +
            "        \"logCosts\": {\n" +
            "            \"href\": \"/openproject/work_packages/7/cost_entries/new\",\n" +
            "            \"type\": \"text/html\",\n" +
            "            \"title\": \"Log costs on Go-Live\"\n" +
            "        },\n" +
            "        \"showCosts\": {\n" +
            "            \"href\": \"/openproject/work_packages/7/cost_entries\",\n" +
            "            \"type\": \"text/html\",\n" +
            "            \"title\": \"Show cost entries\"\n" +
            "        },\n" +
            "        \"costObject\": {\n" +
            "            \"href\": null\n" +
            "        },\n" +
            "        \"costsByType\": {\n" +
            "            \"href\": \"/openproject/api/v3/work_packages/7/summarized_costs_by_type\"\n" +
            "        }\n" +
            "    }\n" +
            "}";
    static String workpackageLists="";

    /**
     * 将返回的json转化为一个task对象
     * @param json
     * @return
     */
    public static TaskVO  JsonToTask(JSONObject json){
        TaskVO task=new TaskVO();
        int taskId = Integer.valueOf(json.getString("id"));
        task.setTaskId(taskId);
        String taskName= json.getString("subject");
        task.setTaskName(taskName);
        int progress = json.getInt("percentageDone");
        task.setProgress(progress+"%");
        String startTime = json.getString("startDate");
        task.setStartTime(startTime);
        String dueTime = json.getString("dueDate");
        task.setStartTime(dueTime);
        String endTime = String.valueOf(json.getOrDefault("customField2","null"));
        task.setEndTime(endTime);
        Object timeSpent = json.getOrDefault("customField3","null");
        if ("null".equals(timeSpent.toString()))
            task.setTimeSpent(0);
        else
            task.setTimeSpent(Integer.valueOf(timeSpent.toString()));
        String descriptionEntity = json.getString("description");
        JSONObject descriptionJsonObject = JSONObject.fromObject(descriptionEntity);
        String description = descriptionJsonObject.getString("raw");
        task.setTaskDetail(description);
        String links = json.getString("_links");
        JSONObject linksJsonObject = JSONObject.fromObject(links);
        Object taskTypeEntity = linksJsonObject.getOrDefault("customField1",null);
        JSONObject taskTypeJsonObject = JSONObject.fromObject(taskTypeEntity.toString());
        String taskType = taskTypeJsonObject.getString("title");
        task.setTaskType(taskType);
        //String spentTime = json.getString("spentTime");
        //返回的spentTime字段格式为PT3H，PT3H15M，PT30M
        //task.setTimeSpent(timeSpentTransfer(spentTime));
        String embedded = json.getString("_embedded");
        JSONObject embeddedJsonObject = JSONObject.fromObject(embedded);
        String priorityEntity = embeddedJsonObject.getString("priority");
        JSONObject priorityJsonObject = JSONObject.fromObject(priorityEntity);
        int priorityId = priorityJsonObject.getInt("id");
        String statusEntity = embeddedJsonObject.getString("status");
        JSONObject statusJsonObject = JSONObject.fromObject(statusEntity);
        String status = statusJsonObject.getString("name");
        String projectEntity = embeddedJsonObject.getString("project");
        JSONObject projectJsonObject = JSONObject.fromObject(projectEntity);
        String projectName = projectJsonObject.getString("name");



        task.setTaskPriority(priorityId);
        task.setStatus(status);
        task.setProjectName(projectName);

        System.out.println(task.toString());
        return task;
    }

    public static List<TaskVO> JsonToTaskList (JSONObject json){
        List<TaskVO> taskVOList =new ArrayList<TaskVO>();
        String embedded = json.getString("_embedded");
        JSONObject embeddedJsonObject = JSONObject.fromObject(embedded);
        JSONArray elementsArray = embeddedJsonObject.getJSONArray("elements");
        for (int i = 0; i < elementsArray.size(); i++) {
            //System.out.println(elementsArray.getJSONObject(i));
            TaskVO task=new TaskVO();
            int id = elementsArray.getJSONObject(i).getInt("id");
            //task.setTaskId(taskId);
        }

        return taskVOList;
    }

    private static double timeSpentTransfer(String s){
        double result=0;
        double hours=0;
        double mins=0;
        if(s.contains("H")){
            if (s.contains("M")){
                int index=s.indexOf("H");
                hours=Integer.valueOf(s.substring(2,index));
                mins=Integer.valueOf(s.substring(index+1,s.length()-1));
                result=Double.valueOf(String.format("%.2f", hours+mins/60));
            }
            else {
                result=Integer.valueOf(s.substring(2,s.length()-1));
            }
        }
        else{
            result=Double.valueOf(String.format("%.2f", Double.valueOf(s.substring(2,s.length()-1))/60));
        }
        return result;
    }

    public static void main(String[] args) {
        JSONObject json = JSONObject.fromObject(milestones);
        JsonToTask(json);
    }
}
