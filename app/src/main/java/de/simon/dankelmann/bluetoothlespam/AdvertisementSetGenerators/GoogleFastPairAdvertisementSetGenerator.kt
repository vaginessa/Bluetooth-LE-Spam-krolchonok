package de.simon.dankelmann.bluetoothlespam.AdvertisementSetGenerators

import android.bluetooth.BluetoothDevice
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.AdvertisingSetParameters
import android.os.ParcelUuid
import de.simon.dankelmann.bluetoothlespam.Callbacks.GenericAdvertisingCallback
import de.simon.dankelmann.bluetoothlespam.Callbacks.GenericAdvertisingSetCallback
import de.simon.dankelmann.bluetoothlespam.Enums.AdvertiseMode
import de.simon.dankelmann.bluetoothlespam.Enums.AdvertisementTarget
import de.simon.dankelmann.bluetoothlespam.Enums.PrimaryPhy
import de.simon.dankelmann.bluetoothlespam.Enums.SecondaryPhy
import de.simon.dankelmann.bluetoothlespam.Enums.TxPowerLevel
import de.simon.dankelmann.bluetoothlespam.Helpers.StringHelpers
import de.simon.dankelmann.bluetoothlespam.Models.AdvertisementSet
import de.simon.dankelmann.bluetoothlespam.Models.ServiceData
import java.util.UUID

class GoogleFastPairAdvertisementSetGenerator:IAdvertisementSetGenerator{

    // Genuine Device Id's taken from here:
    // https://github.com/Flipper-XFW/Xtreme-Firmware/blob/dev/applications/external/ble_spam/protocols/fastpair.c

    val _genuineDeviceIds = mapOf(
        "0001F0" to "Bisto CSR8670 Dev Board",
        "000047" to "Arduino 101",
        "470000" to "Arduino 101 2",
        "00000A" to "Anti-Spoof Test",
        "0A0000" to "Anti-Spoof Test 2",
        "00000B" to "Google Gphones",
        "0B0000" to "Google Gphones 2",
        "0C0000" to "Google Gphones 3",
        "00000D" to "Test 00000D",
        "000007" to "Android Auto",
        "070000" to "Android Auto 2",
        "000008" to "Foocorp Foophones",
        "080000" to "Foocorp Foophones 2",
        "000009" to "Test Android TV",
        "090000" to "Test Android TV 2",
        "000035" to "Test 000035",
        "350000" to "Test 000035 2",
        "000048" to "Fast Pair Headphones",
        "480000" to "Fast Pair Headphones 2",
        "000049" to "Fast Pair Headphones 3",
        "490000" to "Fast Pair Headphones 4",
        "001000" to "LG HBS1110",
        "00B727" to "Smart Controller 1",
        "01E5CE" to "BLE-Phone",
        "0200F0" to "Goodyear",
        "00F7D4" to "Smart Setup",
        "F00002" to "Goodyear",
        "F00400" to "T10",
        "1E89A7" to "ATS2833_EVB",
        "00000C" to "Google Gphones Transfer",
        "0577B1" to "Galaxy S23 Ultra",
        "05A9BC" to "Galaxy S20+",
        "CD8256" to "Bose NC 700",
        "0000F0" to "Bose QuietComfort 35 II",
        "F00000" to "Bose QuietComfort 35 II 2",
        "821F66" to "JBL Flip 6",
        "F52494" to "JBL Buds Pro",
        "718FA4" to "JBL Live 300TWS",
        "0002F0" to "JBL Everest 110GA",
        "92BBBD" to "Pixel Buds",
        "000006" to "Google Pixel buds",
        "060000" to "Google Pixel buds 2",
        "D446A7" to "Sony XM5",
        "2D7A23" to "Sony WF-1000XM4",
        "0E30C3" to "Razer Hammerhead TWS",
        "72EF8D" to "Razer Hammerhead TWS X",
        "72FB00" to "Soundcore Spirit Pro GVA",
        "0003F0" to "LG HBS-835S",
        "002000" to "AIAIAI TMA-2 (H60)",
        "003000" to "Libratone Q Adapt On-Ear",
        "003001" to "Libratone Q Adapt On-Ear 2",
        "00A168" to "boAt  Airdopes 621",
        "00AA48" to "Jabra Elite 2",
        "00AA91" to "Beoplay E8 2.0",
        "00C95C" to "Sony WF-1000X",
        "01EEB4" to "WH-1000XM4",
        "02AA91" to "B&O Earset",
        "01C95C" to "Sony WF-1000X",
        "02D815" to "ATH-CK1TW",
        "035764" to "PLT V8200 Series",
        "038CC7" to "JBL TUNE760NC",
        "02DD4F" to "JBL TUNE770NC",
        "02E2A9" to "TCL MOVEAUDIO S200",
        "035754" to "Plantronics PLT_K2",
        "02C95C" to "Sony WH-1000XM2",
        "038B91" to "DENON AH-C830NCW",
        "02F637" to "JBL LIVE FLEX",
        "02D886" to "JBL REFLECT MINI NC",
        "F00000" to "Bose QuietComfort 35 II",
        "F00001" to "Bose QuietComfort 35 II",
        "F00201" to "JBL Everest 110GA",
        "F00204" to "JBL Everest 310GA",
        "F00209" to "JBL LIVE400BT",
        "F00205" to "JBL Everest 310GA",
        "F00200" to "JBL Everest 110GA",
        "F00208" to "JBL Everest 710GA",
        "F00207" to "JBL Everest 710GA",
        "F00206" to "JBL Everest 310GA",
        "F0020A" to "JBL LIVE400BT",
        "F0020B" to "JBL LIVE400BT",
        "F0020C" to "JBL LIVE400BT",
        "F00203" to "JBL Everest 310GA",
        "F00202" to "JBL Everest 110GA",
        "F00213" to "JBL LIVE650BTNC",
        "F0020F" to "JBL LIVE500BT",
        "F0020E" to "JBL LIVE500BT",
        "F00214" to "JBL LIVE650BTNC",
        "F00212" to "JBL LIVE500BT",
        "F0020D" to "JBL LIVE400BT",
        "F00211" to "JBL LIVE500BT",
        "F00215" to "JBL LIVE650BTNC",
        "F00210" to "JBL LIVE500BT",
        "F00305" to "LG HBS-1500",
        "F00304" to "LG HBS-1010",
        "F00308" to "LG HBS-1125",
        "F00303" to "LG HBS-930",
        "F00306" to "LG HBS-1700",
        "F00300" to "LG HBS-835S",
        "F00309" to "LG HBS-2000",
        "F00302" to "LG HBS-830",
        "F00307" to "LG HBS-1120",
        "F00301" to "LG HBS-835",
        "F00E97" to "JBL VIBE BEAM",
        "04ACFC" to "JBL WAVE BEAM",
        "04AA91" to "Beoplay H4",
        "04AFB8" to "JBL TUNE 720BT",
        "05A963" to "WONDERBOOM 3",
        "05AA91" to "B&O Beoplay E6",
        "05C452" to "JBL LIVE220BT",
        "05C95C" to "Sony WI-1000X",
        "0602F0" to "JBL Everest 310GA",
        "0603F0" to "LG HBS-1700",
        "1E8B18" to "SRS-XB43",
        "1E955B" to "WI-1000XM2",
        "1EC95C" to "Sony WF-SP700N",
        "1ED9F9" to "JBL WAVE FLEX",
        "1EE890" to "ATH-CKS30TW WH",
        "1EEDF5" to "Teufel REAL BLUE TWS 3",
        "1F1101" to "TAG Heuer Calibre E4 45mm",
        "1F181A" to "LinkBuds S",
        "1F2E13" to "Jabra Elite 2",
        "1F4589" to "Jabra Elite 2",
        "1F4627" to "SRS-XG300",
        "1F5865" to "boAt Airdopes 441",
        "1FBB50" to "WF-C700N",
        "1FC95C" to "Sony WF-SP700N",
        "1FE765" to "TONE-TF7Q",
        "1FF8FA" to "JBL REFLECT MINI NC",
        "201C7C" to "SUMMIT",
        "202B3D" to "Amazfit PowerBuds",
        "20330C" to "SRS-XB33",
        "003B41" to "M&D MW65",
        "003D8A" to "Cleer FLOW II",
        "005BC3" to "Panasonic RP-HD610N",
        "008F7D" to "soundcore Glow Mini",
        "00FA72" to "Pioneer SE-MS9BN",
        "0100F0" to "Bose QuietComfort 35 II",
        "011242" to "Nirvana Ion",
        "013D8A" to "Cleer EDGE Voice",
        "01AA91" to "Beoplay H9 3rd Generation",
        "038F16" to "Beats Studio Buds",
        "039F8F" to "Michael Kors Darci 5e",
        "03AA91" to "B&O Beoplay H8i",
        "03B716" to "YY2963",
        "03C95C" to "Sony WH-1000XM2",
        "03C99C" to "MOTO BUDS 135",
        "03F5D4" to "Writing Account Key",
        "045754" to "Plantronics PLT_K2",
        "045764" to "PLT V8200 Series",
        "04C95C" to "Sony WI-1000X",
        "050F0C" to "Major III Voice",
        "052CC7" to "MINOR III",
        "057802" to "TicWatch Pro 5",
        "0582FD" to "Pixel Buds",
        "058D08" to "WH-1000XM4",
        "06AE20" to "Galaxy S21 5G",
        "06C197" to "OPPO Enco Air3 Pro",
        "06C95C" to "Sony WH-1000XM2",
        "06D8FC" to "soundcore Liberty 4 NC",
        "0744B6" to "Technics EAH-AZ60M2",
        "07A41C" to "WF-C700N",
        "07C95C" to "Sony WH-1000XM2",
        "07F426" to "Nest Hub Max",
        "0102F0" to "JBL Everest 110GA - Gun Metal",
        "0202F0" to "JBL Everest 110GA - Silver",
        "0302F0" to "JBL Everest 310GA - Brown",
        "0402F0" to "JBL Everest 310GA - Gun Metal",
        "0502F0" to "JBL Everest 310GA - Silver",
        "0702F0" to "JBL Everest 710GA - Gun Metal",
        "0802F0" to "JBL Everest 710GA - Silver",
        "054B2D" to "JBL TUNE125TWS",
        "0660D7" to "JBL LIVE770NC",
        "0103F0" to "LG HBS-835",
        "0203F0" to "LG HBS-830",
        "0303F0" to "LG HBS-930",
        "0403F0" to "LG HBS-1010",
        "0503F0" to "LG HBS-1500",
        "0703F0" to "LG HBS-1120",
        "0803F0" to "LG HBS-1125",
        "0903F0" to "LG HBS-2000",
        "0102F0" to "JBL Everest 110GA",
        "0202F0" to "JBL Everest 110GA",
        "0302F0" to "JBL Everest 310GA",
        "0402F0" to "JBL Everest 310GA",
        "0502F0" to "JBL Everest 310GA",
        "060000" to "Google Pixel Buds",
        "070000" to "Android Auto",
        "0702F0" to "JBL Everest 710GA",
        "071C74" to "JBL Flip 6",
        "080000" to "Foocorp Foophones",
        "0802F0" to "JBL Everest 710GA",
        "090000" to "Test Android TV",
        "0A0000" to "Test 00000a - Anti-Spoofing",
        "0B0000" to "Google Gphones",
        "0C0000" to "Google Gphones",
        "0DC6BF" to "My Awesome Device II",
        "0DC95C" to "Sony WH-1000XM3",
        "0DEC2B" to "Emporio Armani EA Connected",
        "0E138D" to "WF-SP800N",
        "0EC95C" to "Sony WI-C600N",
        "0ECE95" to "Philips TAT3508",
        "0F0993" to "COUMI TWS-834A",
        "0F1B8D" to "JBL VIBE BEAM",
        "0F232A" to "JBL TUNE BUDS",
        "0F2D16" to "WH-CH520",
        "20A19B" to "WF-SP800N",
        "20C95C" to "Sony WF-SP700N",
        "20CC2C" to "SRS-XB43",
        "213C8C" to "DIZO Wireless Power",
        "21521D" to "boAt Rockerz 355 (Green)",
        "21A04E" to "oraimo FreePods Pro",
        "2D7A23" to "WF-1000XM4",
        "350000" to "Test 000035",
        "470000" to "Arduino 101",
        "480000" to "Fast Pair Headphones",
        "490000" to "Fast Pair Headphones",
        "5BA9B5" to "WF-SP800N",
        "5BACD6" to "Bose QC Ultra Earbuds",
        "5BD6C9" to "JBL TUNE225TWS",
        "5BE3D4" to "JBL Flip 6",
        "5C0206" to "UA | JBL TWS STREAK",
        "5C0C84" to "JBL TUNE225TWS",
        "5C4833" to "WH-CH720N",
        "5C4A7E" to "LG HBS-XL7",
        "5C55E7" to "TCL MOVEAUDIO S200",
        "5C7CDC" to "WH-1000XM5",
        "5C8AA5" to "JBL LIVE220BT",
        "5CC900" to "Sony WF-1000X",
        "5CC901" to "Sony WF-1000X",
        "5CC902" to "Sony WH-1000XM2",
        "5CC903" to "Sony WH-1000XM2",
        "5CC904" to "Sony WI-1000X",
        "5CC905" to "Sony WI-1000X",
        "5CC906" to "Sony WH-1000XM2",
        "5CC907" to "Sony WH-1000XM2",
        "5CC908" to "Sony WI-1000X",
        "5CC909" to "Sony WI-1000X",
        "5CC90A" to "Sony WH-1000XM3",
        "5CC90B" to "Sony WH-1000XM3",
        "5CC90C" to "Sony WH-1000XM3",
        "5CC90D" to "Sony WH-1000XM3",
        "5CC90E" to "Sony WI-C600N",
        "5CC90F" to "Sony WI-C600N",
        "5CC910" to "Sony WI-C600N",
        "5CC911" to "Sony WI-C600N",
        "5CC912" to "Sony WI-C600N",
        "5CC913" to "Sony WI-C600N",
        "5CC914" to "Sony WI-SP600N",
        "5CC915" to "Sony WI-SP600N",
        "5CC916" to "Sony WI-SP600N",
        "5CC917" to "Sony WI-SP600N",
        "5CC918" to "Sony WI-SP600N",
        "5CC919" to "Sony WI-SP600N",
        "5CC91A" to "Sony WI-SP600N",
        "5CC91B" to "Sony WI-SP600N",
        "5CC91C" to "Sony WI-SP600N",
        "5CC91D" to "Sony WI-SP600N",
        "5CC91E" to "Sony WF-SP700N",
        "5CC91F" to "Sony WF-SP700N",
        "5CC920" to "Sony WF-SP700N",
        "5CC921" to "Sony WF-SP700N",
        "5CC922" to "Sony WF-SP700N",
        "5CC923" to "Sony WF-SP700N",
        "5CC924" to "Sony WF-SP700N",
        "5CC925" to "Sony WF-SP700N",
        "5CC926" to "Sony WF-SP700N",
        "5CC927" to "Sony WF-SP700N",
        "5CC928" to "Sony WH-H900N",
        "5CC929" to "Sony WH-H900N",
        "5CC92A" to "Sony WH-H900N",
        "5CC92B" to "Sony WH-H900N",
        "5CC92C" to "Sony WH-H900N",
        "5CC92D" to "Sony WH-H900N",
        "5CC92E" to "Sony WH-H900N",
        "5CC92F" to "Sony WH-H900N",
        "5CC930" to "Sony WH-H900N",
        "5CC931" to "Sony WH-H900N",
        "5CC932" to "Sony WH-CH700N",
        "5CC933" to "Sony WH-CH700N",
        "5CC934" to "Sony WH-CH700N",
        "5CC935" to "Sony WH-CH700N",
        "5CC936" to "Sony WH-CH700N",
        "5CC937" to "Sony WH-CH700N",
        "5CC938" to "Sony WF-1000XM3",
        "5CC939" to "Sony WF-1000XM3",
        "5CC93A" to "Sony WF-1000XM3",
        "5CC93B" to "Sony WF-1000XM3",
        "5CC93C" to "Sony WH-XB700",
        "5CC93D" to "Sony WH-XB700",
        "5CC93E" to "Sony WH-XB700",
        "5CC93F" to "Sony WH-XB700",
        "5CC940" to "Sony WH-XB900N",
        "5CC941" to "Sony WH-XB900N",
        "5CC942" to "Sony WH-XB900N",
        "5CC943" to "Sony WH-XB900N",
        "5CC944" to "Sony WH-XB900N",
        "5CC945" to "Sony WH-XB900N",
        "5CEE3C" to "Fitbit Charge 4",
        "6AD226" to "TicWatch Pro 3",
        "6B1C64" to "Pixel Buds",
        "6B8C65" to "oraimo FreePods 4",
        "6B9304" to "Nokia SB-101",
        "6BA5C3" to "Jabra Elite 4",
        "6C42C0" to "TWS05",
        "6C4DE5" to "JBL LIVE PRO 2 TWS",
        "718FA4" to "JBL LIVE300TWS",
        "89BAD5" to "Galaxy A23 5G",
        "8A31B7" to "Bose QC Ultra Headphones",
        "8A3D00" to "Cleer FLOW Ⅱ",
        "8A3D01" to "Cleer EDGE Voice",
        "8A8F23" to "WF-1000XM5",
        "8AADAE" to "JLab GO Work 2",
        "8B0A91" to "Jabra Elite 5",
        "8B5A7B" to "TicWatch Pro 3 GPS",
        "8B66AB" to "Pixel Buds A-Series",
        "8BB0A0" to "Nokia Solo Bud+",
        "8BF79A" to "Oladance Whisper E1",
        "8C07D2" to "Jabra Elite 4 Active",
        "8C1706" to "YY7861E",
        "8C4236" to "GLIDiC mameBuds",
        "8C6B6A" to "realme Buds Air 3S",
        "8CAD81" to "KENWOOD WS-A1",
        "8CB05C" to "JBL LIVE PRO+ TWS",
        "8CD10F" to "realme Buds Air Pro",
        "8D13B9" to "BLE-TWS",
        "8D16EA" to "Galaxy M14 5G",
        "8D5B67" to "Pixel 90c",
        "8E14D7" to "LG-TONE-TFP8",
        "8E1996" to "Galaxy A24 5g",
        "8E4666" to "Oladance Wearable Stereo",
        "8E5550" to "boAt Airdopes 511v2",
        "9101F0" to "Jabra Elite 2",
        "9128CB" to "TCL MOVEAUDIO Neo",
        "913B0C" to "YH-E700B",
        "915CFA" to "Galaxy A14",
        "9171BE" to "Jabra Evolve2 65 Flex",
        "917E46" to "LinkBuds",
        "91AA00" to "Beoplay E8 2.0",
        "91AA01" to "Beoplay H9 3rd Generation",
        "91AA02" to "B&O Earset",
        "91AA03" to "B&O Beoplay H8i",
        "91AA04" to "Beoplay H4",
        "91AA05" to "B&O Beoplay E6",
        "91BD38" to "LG HBS-FL7",
        "91C813" to "JBL TUNE770NC",
        "91DABC" to "SRS-XB33",
        "92255E" to "LG-TONE-FP6",
        "989D0A" to "Set up your new Pixel 2",
        "9939BC" to "ATH-SQ1TW",
        "994374" to "EDIFIER W320TN",
        "997B4A" to "UA | JBL True Wireless Flash X",
        "99C87B" to "WH-H810 (h.ear)",
        "99D7EA" to "oraimo OpenCirclet",
        "99F098" to "Galaxy S22 Ultra",
        "9A408A" to "MOTO BUDS 065",
        "9A9BDD" to "WH-XB910N",
        "9ADB11" to "Pixel Buds Pro",
        "9AEEA4" to "LG HBS-FN4",
        "9B7339" to "AKG N9 Hybrid",
        "9B735A" to "JBL RFL FLOW PRO",
        "9B9872" to "Hyundai",
        "9BC64D" to "JBL TUNE225TWS",
        "9BE931" to "WI-C100",
        "9C0AF7" to "JBL VIBE BUDS",
        "9C3997" to "ATH-M50xBT2",
        "9C4058" to "JBL WAVE FLEX",
        "9C6BC0" to "LinkBuds S",
        "9C888B" to "WH-H910N (h.ear)",
        "9C98DB" to "JBL TUNE225TWS",
        "9CA277" to "YY2963",
        "9CB5F3" to "WH-1000XM5",
        "9CB881" to "soundcore Motion 300",
        "9CD0F3" to "LG HBS-TFN7",
        "9CE3C7" to "EDIFIER NeoBuds Pro 2",
        "9CEFD1" to "SRS-XG500",
        "9CF08F" to "JLab Epic Air ANC",
        "9D00A6" to "Urbanears Juno",
        "9D7D42" to "Galaxy S20",
        "9DB896" to "Your BMW",
        "A7E52B" to "Bose NC 700 Headphones",
        "A7EF76" to "JBL CLUB PRO+ TWS",
        "A8001A" to "JBL CLUB ONE",
        "A83C10" to "adidas Z.N.E. 01",
        "A8658F" to "ROCKSTER GO",
        "A8845A" to "oraimo FreePods 4",
        "A88B69" to "WF-SP800N",
        "A8A00E" to "Nokia CB-201",
        "A8A72A" to "JBL LIVE670NC",
        "A8C636" to "JBL TUNE660NC",
        "A8CAAD" to "Galaxy F04",
        "A8E353" to "JBL TUNE BEAM",
        "A8F96D" to "JBL ENDURANCE RUN 2 WIRELESS",
        "A90358" to "JBL LIVE220BT",
        "A92498" to "JBL WAVE BUDS",
        "A9394A" to "JBL TUNE230NC TWS",
        "C6936A" to "JBL LIVE PRO+ TWS",
        "C69AFD" to "WF-H800 (h.ear)",
        "C6ABEA" to "UA | JBL True Wireless Flash X",
        "C6EC5F" to "SRS-XE300",
        "C7736C" to "Philips PH805",
        "C79B91" to "Jabra Evolve2 75",
        "C7A267" to "Fake Test Mouse",
        "C7D620" to "JBL Pulse 5",
        "C7FBCC" to "JBL VIBE FLEX",
        "C8162A" to "LinkBuds S",
        "C85D7A" to "JBL ENDURANCE PEAK II",
        "C8777E" to "Jaybird Vista 2",
        "C878AA" to "SRS-XV800",
        "C8C641" to "Redmi Buds 4 Lite",
        "C8D335" to "WF-1000XM4",
        "C8E228" to "Pixel Buds Pro",
        "C9186B" to "WF-1000XM4",
        "C9836A" to "JBL Xtreme 4",
        "CA7030" to "ATH-TWX7",
        "CAB6B8" to "ATH-M20xBT",
        "CAF511" to "Jaybird Vista 2",
        "CB093B" to "Urbanears Juno",
        "CB529D" to "soundcore Glow",
        "CC438E" to "WH-1000XM4",
        "CC5F29" to "JBL TUNE660NC",
        "CC754F" to "YY2963",
        "CC93A5" to "Sync",
        "CCBB7E" to "MIDDLETON",
        "CD8256" to "Bose NC 700 Headphones",
        "D446A7" to "WH-1000XM5",
        "D5A59E" to "Jabra Elite Speaker",
        "D5B5F7" to "MOTO BUDS 600 ANC",
        "D5C6CE" to "realme TechLife Buds T100",
        "D654CD" to "JBL Xtreme 4",
        "D65F4E" to "Philips Fidelio T2",
        "D69B2B" to "TONE-T80S",
        "D6C195" to "LG HBS-SL5",
        "D6E870" to "Beoplay EX",
        "D6EE84" to "Rockerz 255 Max",
        "D7102F" to "ATH-SQ1TW SVN",
        "D7E3EB" to "Cleer HALO",
        "D8058C" to "MOTIF II A.N.C.",
        "D820EA" to "WH-XB910N",
        "D87A3E" to "Pixel Buds Pro",
        "D8F3BA" to "WH-1000XM5",
        "D8F4E8" to "realme Buds T100",
        "D90617" to "Redmi Buds 4 Active",
        "D933A7" to "JBL ENDURANCE PEAK 3",
        "D9414F" to "JBL SOUNDGEAR SENSE",
        "D97EBA" to "JBL TUNE125TWS",
        "D9964B" to "JBL TUNE670NC",
        "DA0F83" to "SPACE",
        "DA4577" to "Jabra Elite 4 Active",
        "DA5200" to "blackbox TRIP II",
        "DAD3A6" to "Jabra Elite 10",
        "DADE43" to "Chromebox",
        "DAE096" to "adidas RPT-02 SOL",
        "DB8AC7" to "LG TONE-FREE",
        "DBE5B1" to "WF-1000XM4",
        "DC5249" to "WH-H810 (h.ear)",
        "DCF33C" to "JBL REFLECT MINI NC",
        "DD4EC0" to "OPPO Enco Air3 Pro",
        "DE215D" to "WF-C500",
        "DE577F" to "Teufel AIRY TWS 2",
        "DEC04C" to "SUMMIT",
        "DEDD6F" to "soundcore Space One",
        "DEE8C0" to "Ear (2)",
        "DEEA86" to "Xiaomi Buds 4 Pro",
        "DEF234" to "WH-H810 (h.ear)",
        "DF01E3" to "Sync",
        "DF271C" to "Big Bang e Gen 3",
        "DF42DE" to "TAG Heuer Calibre E4 42mm",
        "DF4B02" to "SRS-XB13",
        "DF9BA4" to "Bose NC 700 Headphones",
        "DFD433" to "JBL REFLECT AERO",
        "E020C1" to "soundcore Motion 300",
        "E06116" to "LinkBuds S",
        "E07634" to "OnePlus Buds Z",
        "E09172" to "JBL TUNE BEAM",
        "E4E457" to "Galaxy S20 5G",
        "E5440B" to "TAG Heuer Calibre E4 45mm",
        "E57363" to "Oladance Wearable Stereo",
        "E57B57" to "Super Device",
        "E5B4B0" to "WF-1000XM5",
        "E5B91B" to "SRS-XB33",
        "E5E2E9" to "Zone Wireless 2",
        "E64613" to "JBL WAVE BEAM",
        "E64CC6" to "Set up your new Pixel 3 XL",
        "E69877" to "JBL REFLECT AERO",
        "E6E37E" to "realme Buds  Air 5 Pro",
        "E6E771" to "ATH-CKS50TW",
        "E6E8B8" to "POCO Pods",
        "E750CE" to "Jabra Evolve2 75",
        "F52494" to "JBL LIVE PRO+ TWS",
        )

    val serviceUuid = ParcelUuid(UUID.fromString("0000fe2c-0000-1000-8000-00805f9b34fb"))

    override fun getAdvertisementSets():List<AdvertisementSet> {
        var advertisementSets:MutableList<AdvertisementSet> = mutableListOf()

        _genuineDeviceIds.map {

            var advertisementSet:AdvertisementSet = AdvertisementSet()
            advertisementSet.target = AdvertisementTarget.ADVERTISEMENT_TARGET_ANDROID

            // Advertise Settings
            advertisementSet.advertiseSettings.advertiseMode = AdvertiseMode.ADVERTISEMODE_LOW_LATENCY
            advertisementSet.advertiseSettings.txPowerLevel = TxPowerLevel.TX_POWER_HIGH
            advertisementSet.advertiseSettings.connectable = false
            advertisementSet.advertiseSettings.timeout = 0

            // Advertising Parameters
            advertisementSet.advertisingSetParameters.legacyMode = true
            advertisementSet.advertisingSetParameters.interval = AdvertisingSetParameters.INTERVAL_MIN
            advertisementSet.advertisingSetParameters.txPowerLevel = TxPowerLevel.TX_POWER_HIGH
            advertisementSet.advertisingSetParameters.primaryPhy = PrimaryPhy.PHY_LE_1M
            advertisementSet.advertisingSetParameters.secondaryPhy = SecondaryPhy.PHY_LE_1M

            // AdvertiseData
            advertisementSet.advertiseData.includeDeviceName = false

            val serviceData = ServiceData()
            serviceData.serviceUuid = serviceUuid
            serviceData.serviceData = StringHelpers.decodeHex(it.key)
            advertisementSet.advertiseData.services.add(serviceData)
            advertisementSet.advertiseData.includeTxPower = true

            // Scan Response
            //advertisementSet.scanResponse.includeTxPower = true

            // General Data
            advertisementSet.title = it.value

            // Callbacks
            advertisementSet.advertisingSetCallback = GenericAdvertisingSetCallback()
            advertisementSet.advertisingCallback = GenericAdvertisingCallback()

            advertisementSets.add(advertisementSet)
        }

        return advertisementSets.toList()
    }
}