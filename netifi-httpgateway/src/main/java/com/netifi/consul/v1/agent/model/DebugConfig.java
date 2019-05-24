package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DebugConfig {

  @JsonProperty("Bootstrap")
  public boolean bootstrap;

  @JsonProperty("Datacenter")
  public String datacenter;

  @JsonProperty("DataDir")
  public String dataDir;

  @JsonProperty("DNSRecursors")
  public List<String> dnsRecursors;

  @JsonProperty("DNSDomain")
  public String dnsDomain;

  @JsonProperty("LogLevel")
  public String logLevel;

  @JsonProperty("NodeName")
  public String nodeName;

  @JsonProperty("ClientAddrs")
  public List<String> clientAddrs;

  @JsonProperty("BindAddr")
  public String bindAddr;

  @JsonProperty("LeaveOnTerm")
  public boolean leaveOnTerm;

  @JsonProperty("SkipLeaveOnInt")
  public boolean skipLeaveOnInt;

  @JsonProperty("EnableDebug")
  public boolean enableDebug;

  @JsonProperty("VerifyIncoming")
  public boolean verifyIncoming;

  @JsonProperty("VerifyOutgoing")
  public boolean verifyOutgoing;

  @JsonProperty("CAFile")
  public String caFile;

  @JsonProperty("CertFile")
  public String certFile;

  @JsonProperty("KeyFile")
  public String keyFile;

  @JsonProperty("UiDir")
  public Optional<String> uiDir;

  @JsonProperty("PidFile")
  public String pidFile;

  @JsonProperty("EnableSyslog")
  public boolean enableSyslog;

  @JsonProperty("RejoinAfterLeave")
  public boolean rejoinAfterLeave;

  @JsonProperty("AdvertiseAddrLAN")
  public String advertiseAddrLAN;

  @JsonProperty("AdvertiseAddrWAN")
  public String advertiseAddrWAN;

  public boolean isBootstrap() {
    return bootstrap;
  }

  public void setBootstrap(boolean bootstrap) {
    this.bootstrap = bootstrap;
  }

  public String getDatacenter() {
    return datacenter;
  }

  public void setDatacenter(String datacenter) {
    this.datacenter = datacenter;
  }

  public String getDataDir() {
    return dataDir;
  }

  public void setDataDir(String dataDir) {
    this.dataDir = dataDir;
  }

  public List<String> getDnsRecursors() {
    return dnsRecursors;
  }

  public void setDnsRecursors(List<String> dnsRecursors) {
    this.dnsRecursors = dnsRecursors;
  }

  public String getDnsDomain() {
    return dnsDomain;
  }

  public void setDnsDomain(String dnsDomain) {
    this.dnsDomain = dnsDomain;
  }

  public String getLogLevel() {
    return logLevel;
  }

  public void setLogLevel(String logLevel) {
    this.logLevel = logLevel;
  }

  public String getNodeName() {
    return nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public List<String> getClientAddrs() {
    return clientAddrs;
  }

  public void setClientAddrs(List<String> clientAddrs) {
    this.clientAddrs = clientAddrs;
  }

  public String getBindAddr() {
    return bindAddr;
  }

  public void setBindAddr(String bindAddr) {
    this.bindAddr = bindAddr;
  }

  public boolean isLeaveOnTerm() {
    return leaveOnTerm;
  }

  public void setLeaveOnTerm(boolean leaveOnTerm) {
    this.leaveOnTerm = leaveOnTerm;
  }

  public boolean isSkipLeaveOnInt() {
    return skipLeaveOnInt;
  }

  public void setSkipLeaveOnInt(boolean skipLeaveOnInt) {
    this.skipLeaveOnInt = skipLeaveOnInt;
  }

  public boolean isEnableDebug() {
    return enableDebug;
  }

  public void setEnableDebug(boolean enableDebug) {
    this.enableDebug = enableDebug;
  }

  public boolean isVerifyIncoming() {
    return verifyIncoming;
  }

  public void setVerifyIncoming(boolean verifyIncoming) {
    this.verifyIncoming = verifyIncoming;
  }

  public boolean isVerifyOutgoing() {
    return verifyOutgoing;
  }

  public void setVerifyOutgoing(boolean verifyOutgoing) {
    this.verifyOutgoing = verifyOutgoing;
  }

  public String getCaFile() {
    return caFile;
  }

  public void setCaFile(String caFile) {
    this.caFile = caFile;
  }

  public String getCertFile() {
    return certFile;
  }

  public void setCertFile(String certFile) {
    this.certFile = certFile;
  }

  public String getKeyFile() {
    return keyFile;
  }

  public void setKeyFile(String keyFile) {
    this.keyFile = keyFile;
  }

  public Optional<String> getUiDir() {
    return uiDir;
  }

  public void setUiDir(Optional<String> uiDir) {
    this.uiDir = uiDir;
  }

  public String getPidFile() {
    return pidFile;
  }

  public void setPidFile(String pidFile) {
    this.pidFile = pidFile;
  }

  public boolean isEnableSyslog() {
    return enableSyslog;
  }

  public void setEnableSyslog(boolean enableSyslog) {
    this.enableSyslog = enableSyslog;
  }

  public boolean isRejoinAfterLeave() {
    return rejoinAfterLeave;
  }

  public void setRejoinAfterLeave(boolean rejoinAfterLeave) {
    this.rejoinAfterLeave = rejoinAfterLeave;
  }

  public String getAdvertiseAddrLAN() {
    return advertiseAddrLAN;
  }

  public void setAdvertiseAddrLAN(String advertiseAddrLAN) {
    this.advertiseAddrLAN = advertiseAddrLAN;
  }

  public String getAdvertiseAddrWAN() {
    return advertiseAddrWAN;
  }

  public void setAdvertiseAddrWAN(String advertiseAddrWAN) {
    this.advertiseAddrWAN = advertiseAddrWAN;
  }
}