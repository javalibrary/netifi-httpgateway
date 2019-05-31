package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.netifi.consul.v1.catalog.model.TaggedAddresses;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DebugConfig {

  @JsonProperty("ACLAgentMasterToken")
  public String aclAgentMasterToken;

  @JsonProperty("ACLAgentToken")
  public String aclAgentToken;

  @JsonProperty("ACLDatacenter")
  public String aclDatacenter;

  @JsonProperty("ACLDefaultPolicy")
  public String aclDefaultPolicy;

  @JsonProperty("ACLDisabledTTL")
  public String aclDisabledTTL;

  @JsonProperty("ACLDownPolicy")
  public String aclDownPolicy;

  @JsonProperty("ACLEnableKeyListPolicy")
  public String aclEnableKeyListPolicy;

  @JsonProperty("ACLEnforceVersion8")
  public String aclEnforceVersion8;

  @JsonProperty("ACLMasterToken")
  public String aclMasterToken;

  @JsonProperty("ACLPolicyTTL")
  public String aclPolicyTTL;

  @JsonProperty("ACLReplicationToken")
  public String aclReplicationToken;

  @JsonProperty("ACLToken")
  public String aclToken;

  @JsonProperty("ACLTokenReplication")
  public boolean aclTokenReplication;

  @JsonProperty("ACLTokenTTL")
  public String aclTokenTTL;

  @JsonProperty("ACLsEnabled")
  public boolean aclsEnabled;

  @JsonProperty("AEInterval")
  public String aeInterval;

  @JsonProperty("AdvertiseAddrLAN")
  public String advertiseAddrLan;

  @JsonProperty("AdvertiseAddrWAN")
  public String advertiseAddrWAN;

  @JsonProperty("AutopilotCleanupDeadServers")
  public boolean autopilotCleanupDeadServers;

  @JsonProperty("AutopilotDisableUpgradeMigration")
  public boolean autopilotDisableUpgradeMigration;

  @JsonProperty("AutopilotLastContactThreshold")
  public String autopilotLastContactThreshold;

  @JsonProperty("AutopilotMaxTrailingLogs")
  public int autopilotMaxTrailingLogs;

  @JsonProperty("AutopilotRedundancyZoneTag")
  public String autopilotRedundancyZoneTag;

  @JsonProperty("AutopilotServerStabilizationTime")
  public String autopilotServerStabilizationTime;

  @JsonProperty("AutopilotUpgradeVersionTag")
  public String autopilotUpgradeVersionTag;

  @JsonProperty("BindAddr")
  public String bindAddr;

  @JsonProperty("Bootstrap")
  public boolean bootstrap;

  @JsonProperty("BootstrapExpect")
  public int bootstrapExpect;

  @JsonProperty("CAFile")
  public String caFile;

  @JsonProperty("CAPath")
  public String caPath;

  @JsonProperty("CertFile")
  public String certFile;

  @JsonProperty("CheckDeregisterIntervalMin")
  public String checkDeregisterIntervalMin;

  @JsonProperty("CheckReapInterval")
  public String checkReapInterval;

  @JsonProperty("CheckUpdateInterval")
  public String checkUpdateInterval;
  // TODO: checks
  @JsonProperty("ClientAddrs")
  public List<String> clientAddrs;
  // TODO: connectCAConfig
  @JsonProperty("ConnectCAProvider")
  public boolean connectEnabled;

  @JsonProperty("ConnectProxyAllowManagedAPIRegistration")
  public boolean connectProxyAllowManagedAPIRegistration;

  @JsonProperty("ConnectProxyAllowManagedRoot")
  public boolean connectProxyAllowManagedRoot;

  @JsonProperty("ConnectProxyBindMaxPort")
  public int connectProxyBindMaxPort;

  @JsonProperty("ConnectProxyBindMinPort")
  public int connectProxyBindMinPort;
  // TODO: connectProxyDefaultConfig
  @JsonProperty("ConnectProxyDefaultDaemonCommand")
  public List<String> connectProxyDefaultDaemonCommand;

  @JsonProperty("ConnectProxyDefaultExecMode")
  public String connectProxyDefaultExecMode;

  @JsonProperty("ConnectProxyDefaultScriptCommand")
  public List<String> connectProxyDefaultScriptCommand;

  @JsonProperty("ConnectReplicationToken")
  public String connectReplicationToken;

  @JsonProperty("ConnectSidecarMaxPort")
  public int connectSidecarMaxPort;

  @JsonProperty("ConnectSidecarMinPort")
  public int connectSidecarMinPort;

  @JsonProperty("ConnectTestDisableManagedProxies")
  public boolean connectTestDisableManagedProxies;

  @JsonProperty("ConsulCoordinateUpdateBatchSize")
  public int consulCoordinateUpdateBatchSize;

  @JsonProperty("ConsulCoordinateUpdateMaxBatches")
  public int consulCoordinateUpdateMaxBatches;

  @JsonProperty("ConsulCoordinateUpdatePeriod")
  public String consulCoordinateUpdatePeriod;

  @JsonProperty("ConsulRaftElectionTimeout")
  public String consulRaftElectionTimeout;

  @JsonProperty("ConsulRaftHeartbeatTimeout")
  public String consulRaftHeartbeatTimeout;

  @JsonProperty("ConsulRaftLeaderLeaseTimeout")
  public String consulRaftLeaderLeaseTimeout;

  @JsonProperty("ConsulServerHealthInterval")
  public String consulServerHealthInterval;

  @JsonProperty("DNSARecordLimit")
  public int dnsARecordLimit;

  @JsonProperty("DNSAddrs")
  public List<URI> dnsAddrs;

  @JsonProperty("DNSAllowStale")
  public boolean dnsAllowStale;

  @JsonProperty("DNSDisableCompression")
  public boolean dnsDisableCompression;

  @JsonProperty("DNSDomain")
  public String dnsDomain;

  @JsonProperty("DNSEnableTruncate")
  public boolean dnsEnableTruncate;

  @JsonProperty("DNSMaxStale")
  public String dnsMaxStale;

  @JsonProperty("DNSNodeMetaTXT")
  public boolean dnsNodeMetaTXT;

  @JsonProperty("DNSNodeTTL")
  public String dnsNodeTTL;

  @JsonProperty("DNSOnlyPassing")
  public boolean dnsOnlyPassing;

  @JsonProperty("DNSPort")
  public int dnsPort;

  @JsonProperty("DNSRecursorTimeout")
  public String dnsRecursorTimeout;

  @JsonProperty("DNSRecursors")
  public List<String> dnsRecursors;

  @JsonProperty("DNSSOA")
  public DNSSOA dnsSOA;

  @JsonProperty("DNSServiceTTL")
  public Map<String, String> dnsServiceTTL;

  @JsonProperty("DNSUDPAnswerLimit")
  public int dnsUDPAnswerLimit;

  @JsonProperty("DataDir")
  public String dataDir;

  @JsonProperty("Datacenter")
  public String datacenter;

  @JsonProperty("DevMode")
  public boolean devMode;

  @JsonProperty("DisableAnonymousSignature")
  public boolean disableAnonymousSignature;

  @JsonProperty("DisableCoordinates")
  public boolean disableCoordinates;

  @JsonProperty("DisableHTTPUnprintableCharFilter")
  public boolean disableHTTPUnprintableCharFilter;

  @JsonProperty("DisableHostNodeID")
  public boolean disableHostNodeID;

  @JsonProperty("DisableKeyringFile")
  public boolean disableKeyringFile;

  @JsonProperty("DisableRemoteExec")
  public boolean disableRemoteExec;

  @JsonProperty("DisableUpdateCheck")
  public boolean disableUpdateCheck;

  @JsonProperty("DiscardCheckOutput")
  public boolean discardCheckOutput;

  @JsonProperty("DiscoveryMaxStale")
  public String discoveryMaxStale;

  @JsonProperty("EnableAgentTLSForChecks")
  public boolean enableAgentTLSForChecks;

  @JsonProperty("EnableDebug")
  public boolean enableDebug;

  @JsonProperty("EnableLocalScriptChecks")
  public boolean enableLocalScriptChecks;

  @JsonProperty("EnableRemoteScriptChecks")
  public boolean enableRemoteScriptChecks;

  @JsonProperty("EnableSyslog")
  public boolean enableSyslog;

  @JsonProperty("EnableUI")
  public boolean enableUI;

  @JsonProperty("EncryptKey")
  public String encryptKey;

  @JsonProperty("EncryptVerifyIncoming")
  public boolean encryptVerifyIncoming;

  @JsonProperty("EncryptVerifyOutgoing")
  public boolean encryptVerifyOutgoing;

  @JsonProperty("GRPCAddrs")
  public List<URI> grpcAddrs;

  @JsonProperty("GRPCPort")
  public int grpcPort;

  @JsonProperty("GossipLANGossipInterval")
  public String gossipLANGossipInterval;

  @JsonProperty("GossipLANGossipNodes")
  public int gossipLANGossipNodes;

  @JsonProperty("GossipLANProbeInterval")
  public String gossipLANProbeInterval;

  @JsonProperty("GossipLANProbeTimeout")
  public String gossipLANProbeTimeout;

  @JsonProperty("GossipLANRetransmitMult")
  public int gossipLANRetransmitMult;

  @JsonProperty("GossipLANSuspicionMult")
  public int gossipLANSuspicionMult;

  @JsonProperty("GossipWANGossipInterval")
  public String gossipWANGossipInterval;

  @JsonProperty("GossipWANGossipNodes")
  public int gossipWANGossipNodes;

  @JsonProperty("GossipWANProbeInterval")
  public String gossipWANProbeInterval;

  @JsonProperty("GossipWANProbeTimeout")
  public String gossipWANProbeTimeout;

  @JsonProperty("GossipWANRetransmitMult")
  public int gossipWANRetransmitMult;

  @JsonProperty("GossipWANSuspicionMult")
  public int gossipWANSuspicionMult;

  @JsonProperty("HTTPAddrs")
  public List<URI> httpAddrs;

  @JsonProperty("HTTPBlockEndpoints")
  public List<String> httpBlockEndpoints;

  @JsonProperty("HTTPPort")
  public int httpPort;

  @JsonProperty("HTTPResponseHeaders")
  public Map<String, String> httpResponseHeaders;

  @JsonProperty("HTTPSAddrs")
  public List<URI> httpsAddrs;

  @JsonProperty("HTTPSPort")
  public int httpsPort;

  @JsonProperty("KeyFile")
  public String keyFile;

  @JsonProperty("LeaveDrainTime")
  public String leaveDrainTime;

  @JsonProperty("LeaveOnTerm")
  public boolean leaveOnTerm;

  @JsonProperty("LogFile")
  public String logFile;

  @JsonProperty("LogLevel")
  public String logLevel;

  @JsonProperty("LogRotateBytes")
  public int logRotateBytes;

  @JsonProperty("LogRotateDuration")
  public String logRotateDuration;

  @JsonProperty("NodeID")
  public String nodeID;

  @JsonProperty("NodeMeta")
  public Map<String, String> nodeMeta;

  @JsonProperty("NodeName")
  public String nodeName;

  @JsonProperty("NonVotingServer")
  public boolean nonVotingServer;

  @JsonProperty("PidFile")
  public String pidFile;

  @JsonProperty("PrimaryDatacenter")
  public String primaryDatacenter;

  @JsonProperty("RPCAdvertiseAddr")
  public URI rpcAdvertiseAddr;

  @JsonProperty("RPCBindAddr")
  public URI rpcBindAddr;

  @JsonProperty("RPCHoldTimeout")
  public String rpcHoldTimeout;

  @JsonProperty("RPCMaxBurst")
  public int rpcMaxBurst;

  @JsonProperty("RPCProtocol")
  public int rpcProtocol;

  @JsonProperty("RPCRateLimit")
  public int rpcRateLimit;

  @JsonProperty("RaftProtocol")
  public int raftProtocol;

  @JsonProperty("RaftSnapshotInterval")
  public String raftSnapshotInterval;

  @JsonProperty("RaftSnapshotThreshold")
  public int raftSnapshotThreshold;

  @JsonProperty("ReconnectTimeoutLAN")
  public String reconnectTimeoutLAN;

  @JsonProperty("ReconnectTimeoutWAN")
  public String reconnectTimeoutWAN;

  @JsonProperty("RejoinAfterLeave")
  public boolean rejoinAfterLeave;

  @JsonProperty("RetryJoinIntervalLAN")
  public String retryJoinIntervalLAN;

  @JsonProperty("RetryJoinIntervalWAN")
  public String retryJoinIntervalWAN;

  @JsonProperty("RetryJoinLAN")
  public List<String> retryJoinLAN;

  @JsonProperty("RetryJoinMaxAttemptsLAN")
  public int retryJoinMaxAttemptsLAN;

  @JsonProperty("RetryJoinMaxAttemptsWAN")
  public int retryJoinMaxAttemptsWAN;

  @JsonProperty("RetryJoinWAN")
  public List<String> retryJoinWAN;

  @JsonProperty("Revision")
  public String revision;

  @JsonProperty("SegmentLimit")
  public int segmentLimit;

  @JsonProperty("SegmentName")
  public String segmentName;

  @JsonProperty("SegmentNameLimit")
  public int segmentNameLimit;

  @JsonProperty("Segments")
  public List<NetworkSegment> segments;

  @JsonProperty("SerfAdvertiseAddrLAN")
  public URI serfAdvertiseAddrLAN;

  @JsonProperty("SerfAdvertiseAddrWAN")
  public URI serfAdvertiseAddrWAN;

  @JsonProperty("SerfBindAddrLAN")
  public URI serfBindAddrLAN;

  @JsonProperty("SerfBindAddrWAN")
  public URI serfBindAddrWAN;

  @JsonProperty("SerfPortLAN")
  public int serfPortLAN;

  @JsonProperty("SerfPortWAN")
  public int serfPortWAN;

  @JsonProperty("ServerMode")
  public boolean serverMode;

  @JsonProperty("ServerName")
  public String serverName;

  @JsonProperty("ServerPort")
  public int serverPort;
  // TODO: Services
  @JsonProperty("SessionTTLMin")
  public String sessionTTLMin;

  @JsonProperty("SkipLeaveOnInt")
  public boolean skipLeaveOnInt;

  @JsonProperty("StartJoinAddrsLAN")
  public List<String> startJoinAddrsLAN;

  @JsonProperty("StartJoinAddrsWAN")
  public List<String> startJoinAddrsWAN;

  @JsonProperty("SyncCoordinateIntervalMin")
  public String syncCoordinateIntervalMin;

  @JsonProperty("SyncCoordinateRateTarget")
  public int syncCoordinateRateTarget;

  @JsonProperty("SyslogFacility")
  public String syslogFacility;

  @JsonProperty("TLSCipherSuites")
  public List<Integer> tlsCipherSuites;

  @JsonProperty("TLSMinVersion")
  public String tlsMinVersion;

  @JsonProperty("TLSPreferServerCipherSuites")
  public boolean tlsPreferServerCipherSuites;

  @JsonProperty("TaggedAddresses")
  public TaggedAddresses taggedAddresses;

  @JsonProperty("Telemetry")
  public Telemetry telemetry;

  @JsonProperty("TranslateWANAddrs")
  public boolean translateWANAddrs;

  @JsonProperty("UiDir")
  public Optional<String> uiDir;

  @JsonProperty("UnixSocketGroup")
  public String unixSocketGroup;

  @JsonProperty("UnixSocketMode")
  public String unixSocketMode;

  @JsonProperty("UnixSocketUser")
  public String unixSocketUser;

  @JsonProperty("VerifyIncoming")
  public boolean verifyIncoming;

  @JsonProperty("VerifyIncomingHTTPS")
  public boolean verifyIncomingHTTPS;

  @JsonProperty("VerifyIncomingRPC")
  public boolean verifyIncomingRPC;

  @JsonProperty("VerifyOutgoing")
  public boolean verifyOutgoing;

  @JsonProperty("VerifyServerHostname")
  public boolean verifyServerHostname;

  @JsonProperty("Version")
  public String version;

  @JsonProperty("VersionPrerelease")
  public String versionPrerelease;
  // TODO: Watches

  public String getAclAgentMasterToken() {
    return aclAgentMasterToken;
  }

  public void setAclAgentMasterToken(String aclAgentMasterToken) {
    this.aclAgentMasterToken = aclAgentMasterToken;
  }

  public String getAclAgentToken() {
    return aclAgentToken;
  }

  public void setAclAgentToken(String aclAgentToken) {
    this.aclAgentToken = aclAgentToken;
  }

  public String getAclDatacenter() {
    return aclDatacenter;
  }

  public void setAclDatacenter(String aclDatacenter) {
    this.aclDatacenter = aclDatacenter;
  }

  public String getAclDefaultPolicy() {
    return aclDefaultPolicy;
  }

  public void setAclDefaultPolicy(String aclDefaultPolicy) {
    this.aclDefaultPolicy = aclDefaultPolicy;
  }

  public String getAclDisabledTTL() {
    return aclDisabledTTL;
  }

  public void setAclDisabledTTL(String aclDisabledTTL) {
    this.aclDisabledTTL = aclDisabledTTL;
  }

  public String getAclDownPolicy() {
    return aclDownPolicy;
  }

  public void setAclDownPolicy(String aclDownPolicy) {
    this.aclDownPolicy = aclDownPolicy;
  }

  public String getAclEnableKeyListPolicy() {
    return aclEnableKeyListPolicy;
  }

  public void setAclEnableKeyListPolicy(String aclEnableKeyListPolicy) {
    this.aclEnableKeyListPolicy = aclEnableKeyListPolicy;
  }

  public String getAclEnforceVersion8() {
    return aclEnforceVersion8;
  }

  public void setAclEnforceVersion8(String aclEnforceVersion8) {
    this.aclEnforceVersion8 = aclEnforceVersion8;
  }

  public String getAclMasterToken() {
    return aclMasterToken;
  }

  public void setAclMasterToken(String aclMasterToken) {
    this.aclMasterToken = aclMasterToken;
  }

  public String getAclPolicyTTL() {
    return aclPolicyTTL;
  }

  public void setAclPolicyTTL(String aclPolicyTTL) {
    this.aclPolicyTTL = aclPolicyTTL;
  }

  public String getAclReplicationToken() {
    return aclReplicationToken;
  }

  public void setAclReplicationToken(String aclReplicationToken) {
    this.aclReplicationToken = aclReplicationToken;
  }

  public String getAclToken() {
    return aclToken;
  }

  public void setAclToken(String aclToken) {
    this.aclToken = aclToken;
  }

  public boolean isAclTokenReplication() {
    return aclTokenReplication;
  }

  public void setAclTokenReplication(boolean aclTokenReplication) {
    this.aclTokenReplication = aclTokenReplication;
  }

  public String getAclTokenTTL() {
    return aclTokenTTL;
  }

  public void setAclTokenTTL(String aclTokenTTL) {
    this.aclTokenTTL = aclTokenTTL;
  }

  public boolean isAclsEnabled() {
    return aclsEnabled;
  }

  public void setAclsEnabled(boolean aclsEnabled) {
    this.aclsEnabled = aclsEnabled;
  }

  public String getAeInterval() {
    return aeInterval;
  }

  public void setAeInterval(String aeInterval) {
    this.aeInterval = aeInterval;
  }

  public String getAdvertiseAddrLan() {
    return advertiseAddrLan;
  }

  public void setAdvertiseAddrLan(String advertiseAddrLan) {
    this.advertiseAddrLan = advertiseAddrLan;
  }

  public String getAdvertiseAddrWAN() {
    return advertiseAddrWAN;
  }

  public void setAdvertiseAddrWAN(String advertiseAddrWAN) {
    this.advertiseAddrWAN = advertiseAddrWAN;
  }

  public boolean isAutopilotCleanupDeadServers() {
    return autopilotCleanupDeadServers;
  }

  public void setAutopilotCleanupDeadServers(boolean autopilotCleanupDeadServers) {
    this.autopilotCleanupDeadServers = autopilotCleanupDeadServers;
  }

  public boolean isAutopilotDisableUpgradeMigration() {
    return autopilotDisableUpgradeMigration;
  }

  public void setAutopilotDisableUpgradeMigration(boolean autopilotDisableUpgradeMigration) {
    this.autopilotDisableUpgradeMigration = autopilotDisableUpgradeMigration;
  }

  public String getAutopilotLastContactThreshold() {
    return autopilotLastContactThreshold;
  }

  public void setAutopilotLastContactThreshold(String autopilotLastContactThreshold) {
    this.autopilotLastContactThreshold = autopilotLastContactThreshold;
  }

  public int getAutopilotMaxTrailingLogs() {
    return autopilotMaxTrailingLogs;
  }

  public void setAutopilotMaxTrailingLogs(int autopilotMaxTrailingLogs) {
    this.autopilotMaxTrailingLogs = autopilotMaxTrailingLogs;
  }

  public String getAutopilotRedundancyZoneTag() {
    return autopilotRedundancyZoneTag;
  }

  public void setAutopilotRedundancyZoneTag(String autopilotRedundancyZoneTag) {
    this.autopilotRedundancyZoneTag = autopilotRedundancyZoneTag;
  }

  public String getAutopilotServerStabilizationTime() {
    return autopilotServerStabilizationTime;
  }

  public void setAutopilotServerStabilizationTime(String autopilotServerStabilizationTime) {
    this.autopilotServerStabilizationTime = autopilotServerStabilizationTime;
  }

  public String getAutopilotUpgradeVersionTag() {
    return autopilotUpgradeVersionTag;
  }

  public void setAutopilotUpgradeVersionTag(String autopilotUpgradeVersionTag) {
    this.autopilotUpgradeVersionTag = autopilotUpgradeVersionTag;
  }

  public String getBindAddr() {
    return bindAddr;
  }

  public void setBindAddr(String bindAddr) {
    this.bindAddr = bindAddr;
  }

  public boolean isBootstrap() {
    return bootstrap;
  }

  public void setBootstrap(boolean bootstrap) {
    this.bootstrap = bootstrap;
  }

  public int getBootstrapExpect() {
    return bootstrapExpect;
  }

  public void setBootstrapExpect(int bootstrapExpect) {
    this.bootstrapExpect = bootstrapExpect;
  }

  public String getCaFile() {
    return caFile;
  }

  public void setCaFile(String caFile) {
    this.caFile = caFile;
  }

  public String getCaPath() {
    return caPath;
  }

  public void setCaPath(String caPath) {
    this.caPath = caPath;
  }

  public String getCertFile() {
    return certFile;
  }

  public void setCertFile(String certFile) {
    this.certFile = certFile;
  }

  public String getCheckDeregisterIntervalMin() {
    return checkDeregisterIntervalMin;
  }

  public void setCheckDeregisterIntervalMin(String checkDeregisterIntervalMin) {
    this.checkDeregisterIntervalMin = checkDeregisterIntervalMin;
  }

  public String getCheckReapInterval() {
    return checkReapInterval;
  }

  public void setCheckReapInterval(String checkReapInterval) {
    this.checkReapInterval = checkReapInterval;
  }

  public String getCheckUpdateInterval() {
    return checkUpdateInterval;
  }

  public void setCheckUpdateInterval(String checkUpdateInterval) {
    this.checkUpdateInterval = checkUpdateInterval;
  }

  public List<String> getClientAddrs() {
    return clientAddrs;
  }

  public void setClientAddrs(List<String> clientAddrs) {
    this.clientAddrs = clientAddrs;
  }

  public boolean isConnectEnabled() {
    return connectEnabled;
  }

  public void setConnectEnabled(boolean connectEnabled) {
    this.connectEnabled = connectEnabled;
  }

  public boolean isConnectProxyAllowManagedAPIRegistration() {
    return connectProxyAllowManagedAPIRegistration;
  }

  public void setConnectProxyAllowManagedAPIRegistration(
      boolean connectProxyAllowManagedAPIRegistration) {
    this.connectProxyAllowManagedAPIRegistration = connectProxyAllowManagedAPIRegistration;
  }

  public boolean isConnectProxyAllowManagedRoot() {
    return connectProxyAllowManagedRoot;
  }

  public void setConnectProxyAllowManagedRoot(boolean connectProxyAllowManagedRoot) {
    this.connectProxyAllowManagedRoot = connectProxyAllowManagedRoot;
  }

  public int getConnectProxyBindMaxPort() {
    return connectProxyBindMaxPort;
  }

  public void setConnectProxyBindMaxPort(int connectProxyBindMaxPort) {
    this.connectProxyBindMaxPort = connectProxyBindMaxPort;
  }

  public int getConnectProxyBindMinPort() {
    return connectProxyBindMinPort;
  }

  public void setConnectProxyBindMinPort(int connectProxyBindMinPort) {
    this.connectProxyBindMinPort = connectProxyBindMinPort;
  }

  public List<String> getConnectProxyDefaultDaemonCommand() {
    return connectProxyDefaultDaemonCommand;
  }

  public void setConnectProxyDefaultDaemonCommand(List<String> connectProxyDefaultDaemonCommand) {
    this.connectProxyDefaultDaemonCommand = connectProxyDefaultDaemonCommand;
  }

  public String getConnectProxyDefaultExecMode() {
    return connectProxyDefaultExecMode;
  }

  public void setConnectProxyDefaultExecMode(String connectProxyDefaultExecMode) {
    this.connectProxyDefaultExecMode = connectProxyDefaultExecMode;
  }

  public List<String> getConnectProxyDefaultScriptCommand() {
    return connectProxyDefaultScriptCommand;
  }

  public void setConnectProxyDefaultScriptCommand(List<String> connectProxyDefaultScriptCommand) {
    this.connectProxyDefaultScriptCommand = connectProxyDefaultScriptCommand;
  }

  public String getConnectReplicationToken() {
    return connectReplicationToken;
  }

  public void setConnectReplicationToken(String connectReplicationToken) {
    this.connectReplicationToken = connectReplicationToken;
  }

  public int getConnectSidecarMaxPort() {
    return connectSidecarMaxPort;
  }

  public void setConnectSidecarMaxPort(int connectSidecarMaxPort) {
    this.connectSidecarMaxPort = connectSidecarMaxPort;
  }

  public int getConnectSidecarMinPort() {
    return connectSidecarMinPort;
  }

  public void setConnectSidecarMinPort(int connectSidecarMinPort) {
    this.connectSidecarMinPort = connectSidecarMinPort;
  }

  public boolean isConnectTestDisableManagedProxies() {
    return connectTestDisableManagedProxies;
  }

  public void setConnectTestDisableManagedProxies(boolean connectTestDisableManagedProxies) {
    this.connectTestDisableManagedProxies = connectTestDisableManagedProxies;
  }

  public int getConsulCoordinateUpdateBatchSize() {
    return consulCoordinateUpdateBatchSize;
  }

  public void setConsulCoordinateUpdateBatchSize(int consulCoordinateUpdateBatchSize) {
    this.consulCoordinateUpdateBatchSize = consulCoordinateUpdateBatchSize;
  }

  public int getConsulCoordinateUpdateMaxBatches() {
    return consulCoordinateUpdateMaxBatches;
  }

  public void setConsulCoordinateUpdateMaxBatches(int consulCoordinateUpdateMaxBatches) {
    this.consulCoordinateUpdateMaxBatches = consulCoordinateUpdateMaxBatches;
  }

  public String getConsulCoordinateUpdatePeriod() {
    return consulCoordinateUpdatePeriod;
  }

  public void setConsulCoordinateUpdatePeriod(String consulCoordinateUpdatePeriod) {
    this.consulCoordinateUpdatePeriod = consulCoordinateUpdatePeriod;
  }

  public String getConsulRaftElectionTimeout() {
    return consulRaftElectionTimeout;
  }

  public void setConsulRaftElectionTimeout(String consulRaftElectionTimeout) {
    this.consulRaftElectionTimeout = consulRaftElectionTimeout;
  }

  public String getConsulRaftHeartbeatTimeout() {
    return consulRaftHeartbeatTimeout;
  }

  public void setConsulRaftHeartbeatTimeout(String consulRaftHeartbeatTimeout) {
    this.consulRaftHeartbeatTimeout = consulRaftHeartbeatTimeout;
  }

  public String getConsulRaftLeaderLeaseTimeout() {
    return consulRaftLeaderLeaseTimeout;
  }

  public void setConsulRaftLeaderLeaseTimeout(String consulRaftLeaderLeaseTimeout) {
    this.consulRaftLeaderLeaseTimeout = consulRaftLeaderLeaseTimeout;
  }

  public String getConsulServerHealthInterval() {
    return consulServerHealthInterval;
  }

  public void setConsulServerHealthInterval(String consulServerHealthInterval) {
    this.consulServerHealthInterval = consulServerHealthInterval;
  }

  public int getDnsARecordLimit() {
    return dnsARecordLimit;
  }

  public void setDnsARecordLimit(int dnsARecordLimit) {
    this.dnsARecordLimit = dnsARecordLimit;
  }

  public List<URI> getDnsAddrs() {
    return dnsAddrs;
  }

  public void setDnsAddrs(List<URI> dnsAddrs) {
    this.dnsAddrs = dnsAddrs;
  }

  public boolean isDnsAllowStale() {
    return dnsAllowStale;
  }

  public void setDnsAllowStale(boolean dnsAllowStale) {
    this.dnsAllowStale = dnsAllowStale;
  }

  public boolean isDnsDisableCompression() {
    return dnsDisableCompression;
  }

  public void setDnsDisableCompression(boolean dnsDisableCompression) {
    this.dnsDisableCompression = dnsDisableCompression;
  }

  public String getDnsDomain() {
    return dnsDomain;
  }

  public void setDnsDomain(String dnsDomain) {
    this.dnsDomain = dnsDomain;
  }

  public boolean isDnsEnableTruncate() {
    return dnsEnableTruncate;
  }

  public void setDnsEnableTruncate(boolean dnsEnableTruncate) {
    this.dnsEnableTruncate = dnsEnableTruncate;
  }

  public String getDnsMaxStale() {
    return dnsMaxStale;
  }

  public void setDnsMaxStale(String dnsMaxStale) {
    this.dnsMaxStale = dnsMaxStale;
  }

  public boolean isDnsNodeMetaTXT() {
    return dnsNodeMetaTXT;
  }

  public void setDnsNodeMetaTXT(boolean dnsNodeMetaTXT) {
    this.dnsNodeMetaTXT = dnsNodeMetaTXT;
  }

  public String getDnsNodeTTL() {
    return dnsNodeTTL;
  }

  public void setDnsNodeTTL(String dnsNodeTTL) {
    this.dnsNodeTTL = dnsNodeTTL;
  }

  public boolean isDnsOnlyPassing() {
    return dnsOnlyPassing;
  }

  public void setDnsOnlyPassing(boolean dnsOnlyPassing) {
    this.dnsOnlyPassing = dnsOnlyPassing;
  }

  public int getDnsPort() {
    return dnsPort;
  }

  public void setDnsPort(int dnsPort) {
    this.dnsPort = dnsPort;
  }

  public String getDnsRecursorTimeout() {
    return dnsRecursorTimeout;
  }

  public void setDnsRecursorTimeout(String dnsRecursorTimeout) {
    this.dnsRecursorTimeout = dnsRecursorTimeout;
  }

  public List<String> getDnsRecursors() {
    return dnsRecursors;
  }

  public void setDnsRecursors(List<String> dnsRecursors) {
    this.dnsRecursors = dnsRecursors;
  }

  public DNSSOA getDnsSOA() {
    return dnsSOA;
  }

  public void setDnsSOA(DNSSOA dnsSOA) {
    this.dnsSOA = dnsSOA;
  }

  public Map<String, String> getDnsServiceTTL() {
    return dnsServiceTTL;
  }

  public void setDnsServiceTTL(Map<String, String> dnsServiceTTL) {
    this.dnsServiceTTL = dnsServiceTTL;
  }

  public int getDnsUDPAnswerLimit() {
    return dnsUDPAnswerLimit;
  }

  public void setDnsUDPAnswerLimit(int dnsUDPAnswerLimit) {
    this.dnsUDPAnswerLimit = dnsUDPAnswerLimit;
  }

  public String getDataDir() {
    return dataDir;
  }

  public void setDataDir(String dataDir) {
    this.dataDir = dataDir;
  }

  public String getDatacenter() {
    return datacenter;
  }

  public void setDatacenter(String datacenter) {
    this.datacenter = datacenter;
  }

  public boolean isDevMode() {
    return devMode;
  }

  public void setDevMode(boolean devMode) {
    this.devMode = devMode;
  }

  public boolean isDisableAnonymousSignature() {
    return disableAnonymousSignature;
  }

  public void setDisableAnonymousSignature(boolean disableAnonymousSignature) {
    this.disableAnonymousSignature = disableAnonymousSignature;
  }

  public boolean isDisableCoordinates() {
    return disableCoordinates;
  }

  public void setDisableCoordinates(boolean disableCoordinates) {
    this.disableCoordinates = disableCoordinates;
  }

  public boolean isDisableHTTPUnprintableCharFilter() {
    return disableHTTPUnprintableCharFilter;
  }

  public void setDisableHTTPUnprintableCharFilter(boolean disableHTTPUnprintableCharFilter) {
    this.disableHTTPUnprintableCharFilter = disableHTTPUnprintableCharFilter;
  }

  public boolean isDisableHostNodeID() {
    return disableHostNodeID;
  }

  public void setDisableHostNodeID(boolean disableHostNodeID) {
    this.disableHostNodeID = disableHostNodeID;
  }

  public boolean isDisableKeyringFile() {
    return disableKeyringFile;
  }

  public void setDisableKeyringFile(boolean disableKeyringFile) {
    this.disableKeyringFile = disableKeyringFile;
  }

  public boolean isDisableRemoteExec() {
    return disableRemoteExec;
  }

  public void setDisableRemoteExec(boolean disableRemoteExec) {
    this.disableRemoteExec = disableRemoteExec;
  }

  public boolean isDisableUpdateCheck() {
    return disableUpdateCheck;
  }

  public void setDisableUpdateCheck(boolean disableUpdateCheck) {
    this.disableUpdateCheck = disableUpdateCheck;
  }

  public boolean isDiscardCheckOutput() {
    return discardCheckOutput;
  }

  public void setDiscardCheckOutput(boolean discardCheckOutput) {
    this.discardCheckOutput = discardCheckOutput;
  }

  public String getDiscoveryMaxStale() {
    return discoveryMaxStale;
  }

  public void setDiscoveryMaxStale(String discoveryMaxStale) {
    this.discoveryMaxStale = discoveryMaxStale;
  }

  public boolean isEnableAgentTLSForChecks() {
    return enableAgentTLSForChecks;
  }

  public void setEnableAgentTLSForChecks(boolean enableAgentTLSForChecks) {
    this.enableAgentTLSForChecks = enableAgentTLSForChecks;
  }

  public boolean isEnableDebug() {
    return enableDebug;
  }

  public void setEnableDebug(boolean enableDebug) {
    this.enableDebug = enableDebug;
  }

  public boolean isEnableLocalScriptChecks() {
    return enableLocalScriptChecks;
  }

  public void setEnableLocalScriptChecks(boolean enableLocalScriptChecks) {
    this.enableLocalScriptChecks = enableLocalScriptChecks;
  }

  public boolean isEnableRemoteScriptChecks() {
    return enableRemoteScriptChecks;
  }

  public void setEnableRemoteScriptChecks(boolean enableRemoteScriptChecks) {
    this.enableRemoteScriptChecks = enableRemoteScriptChecks;
  }

  public boolean isEnableSyslog() {
    return enableSyslog;
  }

  public void setEnableSyslog(boolean enableSyslog) {
    this.enableSyslog = enableSyslog;
  }

  public boolean isEnableUI() {
    return enableUI;
  }

  public void setEnableUI(boolean enableUI) {
    this.enableUI = enableUI;
  }

  public String getEncryptKey() {
    return encryptKey;
  }

  public void setEncryptKey(String encryptKey) {
    this.encryptKey = encryptKey;
  }

  public boolean isEncryptVerifyIncoming() {
    return encryptVerifyIncoming;
  }

  public void setEncryptVerifyIncoming(boolean encryptVerifyIncoming) {
    this.encryptVerifyIncoming = encryptVerifyIncoming;
  }

  public boolean isEncryptVerifyOutgoing() {
    return encryptVerifyOutgoing;
  }

  public void setEncryptVerifyOutgoing(boolean encryptVerifyOutgoing) {
    this.encryptVerifyOutgoing = encryptVerifyOutgoing;
  }

  public List<URI> getGrpcAddrs() {
    return grpcAddrs;
  }

  public void setGrpcAddrs(List<URI> grpcAddrs) {
    this.grpcAddrs = grpcAddrs;
  }

  public int getGrpcPort() {
    return grpcPort;
  }

  public void setGrpcPort(int grpcPort) {
    this.grpcPort = grpcPort;
  }

  public String getGossipLANGossipInterval() {
    return gossipLANGossipInterval;
  }

  public void setGossipLANGossipInterval(String gossipLANGossipInterval) {
    this.gossipLANGossipInterval = gossipLANGossipInterval;
  }

  public int getGossipLANGossipNodes() {
    return gossipLANGossipNodes;
  }

  public void setGossipLANGossipNodes(int gossipLANGossipNodes) {
    this.gossipLANGossipNodes = gossipLANGossipNodes;
  }

  public String getGossipLANProbeInterval() {
    return gossipLANProbeInterval;
  }

  public void setGossipLANProbeInterval(String gossipLANProbeInterval) {
    this.gossipLANProbeInterval = gossipLANProbeInterval;
  }

  public String getGossipLANProbeTimeout() {
    return gossipLANProbeTimeout;
  }

  public void setGossipLANProbeTimeout(String gossipLANProbeTimeout) {
    this.gossipLANProbeTimeout = gossipLANProbeTimeout;
  }

  public int getGossipLANRetransmitMult() {
    return gossipLANRetransmitMult;
  }

  public void setGossipLANRetransmitMult(int gossipLANRetransmitMult) {
    this.gossipLANRetransmitMult = gossipLANRetransmitMult;
  }

  public int getGossipLANSuspicionMult() {
    return gossipLANSuspicionMult;
  }

  public void setGossipLANSuspicionMult(int gossipLANSuspicionMult) {
    this.gossipLANSuspicionMult = gossipLANSuspicionMult;
  }

  public String getGossipWANGossipInterval() {
    return gossipWANGossipInterval;
  }

  public void setGossipWANGossipInterval(String gossipWANGossipInterval) {
    this.gossipWANGossipInterval = gossipWANGossipInterval;
  }

  public int getGossipWANGossipNodes() {
    return gossipWANGossipNodes;
  }

  public void setGossipWANGossipNodes(int gossipWANGossipNodes) {
    this.gossipWANGossipNodes = gossipWANGossipNodes;
  }

  public String getGossipWANProbeInterval() {
    return gossipWANProbeInterval;
  }

  public void setGossipWANProbeInterval(String gossipWANProbeInterval) {
    this.gossipWANProbeInterval = gossipWANProbeInterval;
  }

  public String getGossipWANProbeTimeout() {
    return gossipWANProbeTimeout;
  }

  public void setGossipWANProbeTimeout(String gossipWANProbeTimeout) {
    this.gossipWANProbeTimeout = gossipWANProbeTimeout;
  }

  public int getGossipWANRetransmitMult() {
    return gossipWANRetransmitMult;
  }

  public void setGossipWANRetransmitMult(int gossipWANRetransmitMult) {
    this.gossipWANRetransmitMult = gossipWANRetransmitMult;
  }

  public int getGossipWANSuspicionMult() {
    return gossipWANSuspicionMult;
  }

  public void setGossipWANSuspicionMult(int gossipWANSuspicionMult) {
    this.gossipWANSuspicionMult = gossipWANSuspicionMult;
  }

  public List<URI> getHttpAddrs() {
    return httpAddrs;
  }

  public void setHttpAddrs(List<URI> httpAddrs) {
    this.httpAddrs = httpAddrs;
  }

  public List<String> getHttpBlockEndpoints() {
    return httpBlockEndpoints;
  }

  public void setHttpBlockEndpoints(List<String> httpBlockEndpoints) {
    this.httpBlockEndpoints = httpBlockEndpoints;
  }

  public int getHttpPort() {
    return httpPort;
  }

  public void setHttpPort(int httpPort) {
    this.httpPort = httpPort;
  }

  public List<URI> getHttpsAddrs() {
    return httpsAddrs;
  }

  public void setHttpsAddrs(List<URI> httpsAddrs) {
    this.httpsAddrs = httpsAddrs;
  }

  public int getHttpsPort() {
    return httpsPort;
  }

  public void setHttpsPort(int httpsPort) {
    this.httpsPort = httpsPort;
  }

  public Map<String, String> getHttpResponseHeaders() {
    return httpResponseHeaders;
  }

  public void setHttpResponseHeaders(Map<String, String> httpResponseHeaders) {
    this.httpResponseHeaders = httpResponseHeaders;
  }

  public String getKeyFile() {
    return keyFile;
  }

  public void setKeyFile(String keyFile) {
    this.keyFile = keyFile;
  }

  public String getLeaveDrainTime() {
    return leaveDrainTime;
  }

  public void setLeaveDrainTime(String leaveDrainTime) {
    this.leaveDrainTime = leaveDrainTime;
  }

  public boolean isLeaveOnTerm() {
    return leaveOnTerm;
  }

  public void setLeaveOnTerm(boolean leaveOnTerm) {
    this.leaveOnTerm = leaveOnTerm;
  }

  public String getLogFile() {
    return logFile;
  }

  public void setLogFile(String logFile) {
    this.logFile = logFile;
  }

  public String getLogLevel() {
    return logLevel;
  }

  public void setLogLevel(String logLevel) {
    this.logLevel = logLevel;
  }

  public int getLogRotateBytes() {
    return logRotateBytes;
  }

  public void setLogRotateBytes(int logRotateBytes) {
    this.logRotateBytes = logRotateBytes;
  }

  public String getLogRotateDuration() {
    return logRotateDuration;
  }

  public void setLogRotateDuration(String logRotateDuration) {
    this.logRotateDuration = logRotateDuration;
  }

  public String getNodeID() {
    return nodeID;
  }

  public void setNodeID(String nodeID) {
    this.nodeID = nodeID;
  }

  public Map<String, String> getNodeMeta() {
    return nodeMeta;
  }

  public void setNodeMeta(Map<String, String> nodeMeta) {
    this.nodeMeta = nodeMeta;
  }

  public String getNodeName() {
    return nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }

  public boolean isNonVotingServer() {
    return nonVotingServer;
  }

  public void setNonVotingServer(boolean nonVotingServer) {
    this.nonVotingServer = nonVotingServer;
  }

  public String getPidFile() {
    return pidFile;
  }

  public void setPidFile(String pidFile) {
    this.pidFile = pidFile;
  }

  public String getPrimaryDatacenter() {
    return primaryDatacenter;
  }

  public void setPrimaryDatacenter(String primaryDatacenter) {
    this.primaryDatacenter = primaryDatacenter;
  }

  public URI getRpcAdvertiseAddr() {
    return rpcAdvertiseAddr;
  }

  public void setRpcAdvertiseAddr(URI rpcAdvertiseAddr) {
    this.rpcAdvertiseAddr = rpcAdvertiseAddr;
  }

  public URI getRpcBindAddr() {
    return rpcBindAddr;
  }

  public void setRpcBindAddr(URI rpcBindAddr) {
    this.rpcBindAddr = rpcBindAddr;
  }

  public String getRpcHoldTimeout() {
    return rpcHoldTimeout;
  }

  public void setRpcHoldTimeout(String rpcHoldTimeout) {
    this.rpcHoldTimeout = rpcHoldTimeout;
  }

  public int getRpcMaxBurst() {
    return rpcMaxBurst;
  }

  public void setRpcMaxBurst(int rpcMaxBurst) {
    this.rpcMaxBurst = rpcMaxBurst;
  }

  public int getRpcProtocol() {
    return rpcProtocol;
  }

  public void setRpcProtocol(int rpcProtocol) {
    this.rpcProtocol = rpcProtocol;
  }

  public int getRpcRateLimit() {
    return rpcRateLimit;
  }

  public void setRpcRateLimit(int rpcRateLimit) {
    this.rpcRateLimit = rpcRateLimit;
  }

  public int getRaftProtocol() {
    return raftProtocol;
  }

  public void setRaftProtocol(int raftProtocol) {
    this.raftProtocol = raftProtocol;
  }

  public String getRaftSnapshotInterval() {
    return raftSnapshotInterval;
  }

  public void setRaftSnapshotInterval(String raftSnapshotInterval) {
    this.raftSnapshotInterval = raftSnapshotInterval;
  }

  public int getRaftSnapshotThreshold() {
    return raftSnapshotThreshold;
  }

  public void setRaftSnapshotThreshold(int raftSnapshotThreshold) {
    this.raftSnapshotThreshold = raftSnapshotThreshold;
  }

  public String getReconnectTimeoutLAN() {
    return reconnectTimeoutLAN;
  }

  public void setReconnectTimeoutLAN(String reconnectTimeoutLAN) {
    this.reconnectTimeoutLAN = reconnectTimeoutLAN;
  }

  public String getReconnectTimeoutWAN() {
    return reconnectTimeoutWAN;
  }

  public void setReconnectTimeoutWAN(String reconnectTimeoutWAN) {
    this.reconnectTimeoutWAN = reconnectTimeoutWAN;
  }

  public boolean isRejoinAfterLeave() {
    return rejoinAfterLeave;
  }

  public void setRejoinAfterLeave(boolean rejoinAfterLeave) {
    this.rejoinAfterLeave = rejoinAfterLeave;
  }

  public String getRetryJoinIntervalLAN() {
    return retryJoinIntervalLAN;
  }

  public void setRetryJoinIntervalLAN(String retryJoinIntervalLAN) {
    this.retryJoinIntervalLAN = retryJoinIntervalLAN;
  }

  public String getRetryJoinIntervalWAN() {
    return retryJoinIntervalWAN;
  }

  public void setRetryJoinIntervalWAN(String retryJoinIntervalWAN) {
    this.retryJoinIntervalWAN = retryJoinIntervalWAN;
  }

  public List<String> getRetryJoinLAN() {
    return retryJoinLAN;
  }

  public void setRetryJoinLAN(List<String> retryJoinLAN) {
    this.retryJoinLAN = retryJoinLAN;
  }

  public int getRetryJoinMaxAttemptsLAN() {
    return retryJoinMaxAttemptsLAN;
  }

  public void setRetryJoinMaxAttemptsLAN(int retryJoinMaxAttemptsLAN) {
    this.retryJoinMaxAttemptsLAN = retryJoinMaxAttemptsLAN;
  }

  public int getRetryJoinMaxAttemptsWAN() {
    return retryJoinMaxAttemptsWAN;
  }

  public void setRetryJoinMaxAttemptsWAN(int retryJoinMaxAttemptsWAN) {
    this.retryJoinMaxAttemptsWAN = retryJoinMaxAttemptsWAN;
  }

  public List<String> getRetryJoinWAN() {
    return retryJoinWAN;
  }

  public void setRetryJoinWAN(List<String> retryJoinWAN) {
    this.retryJoinWAN = retryJoinWAN;
  }

  public String getRevision() {
    return revision;
  }

  public void setRevision(String revision) {
    this.revision = revision;
  }

  public int getSegmentLimit() {
    return segmentLimit;
  }

  public void setSegmentLimit(int segmentLimit) {
    this.segmentLimit = segmentLimit;
  }

  public String getSegmentName() {
    return segmentName;
  }

  public void setSegmentName(String segmentName) {
    this.segmentName = segmentName;
  }

  public int getSegmentNameLimit() {
    return segmentNameLimit;
  }

  public void setSegmentNameLimit(int segmentNameLimit) {
    this.segmentNameLimit = segmentNameLimit;
  }

  public List<NetworkSegment> getSegments() {
    return segments;
  }

  public void setSegments(List<NetworkSegment> segments) {
    this.segments = segments;
  }

  public URI getSerfAdvertiseAddrLAN() {
    return serfAdvertiseAddrLAN;
  }

  public void setSerfAdvertiseAddrLAN(URI serfAdvertiseAddrLAN) {
    this.serfAdvertiseAddrLAN = serfAdvertiseAddrLAN;
  }

  public URI getSerfAdvertiseAddrWAN() {
    return serfAdvertiseAddrWAN;
  }

  public void setSerfAdvertiseAddrWAN(URI serfAdvertiseAddrWAN) {
    this.serfAdvertiseAddrWAN = serfAdvertiseAddrWAN;
  }

  public URI getSerfBindAddrLAN() {
    return serfBindAddrLAN;
  }

  public void setSerfBindAddrLAN(URI serfBindAddrLAN) {
    this.serfBindAddrLAN = serfBindAddrLAN;
  }

  public URI getSerfBindAddrWAN() {
    return serfBindAddrWAN;
  }

  public void setSerfBindAddrWAN(URI serfBindAddrWAN) {
    this.serfBindAddrWAN = serfBindAddrWAN;
  }

  public int getSerfPortLAN() {
    return serfPortLAN;
  }

  public void setSerfPortLAN(int serfPortLAN) {
    this.serfPortLAN = serfPortLAN;
  }

  public int getSerfPortWAN() {
    return serfPortWAN;
  }

  public void setSerfPortWAN(int serfPortWAN) {
    this.serfPortWAN = serfPortWAN;
  }

  public boolean isServerMode() {
    return serverMode;
  }

  public void setServerMode(boolean serverMode) {
    this.serverMode = serverMode;
  }

  public String getServerName() {
    return serverName;
  }

  public void setServerName(String serverName) {
    this.serverName = serverName;
  }

  public int getServerPort() {
    return serverPort;
  }

  public void setServerPort(int serverPort) {
    this.serverPort = serverPort;
  }

  public String getSessionTTLMin() {
    return sessionTTLMin;
  }

  public void setSessionTTLMin(String sessionTTLMin) {
    this.sessionTTLMin = sessionTTLMin;
  }

  public boolean isSkipLeaveOnInt() {
    return skipLeaveOnInt;
  }

  public void setSkipLeaveOnInt(boolean skipLeaveOnInt) {
    this.skipLeaveOnInt = skipLeaveOnInt;
  }

  public List<String> getStartJoinAddrsLAN() {
    return startJoinAddrsLAN;
  }

  public void setStartJoinAddrsLAN(List<String> startJoinAddrsLAN) {
    this.startJoinAddrsLAN = startJoinAddrsLAN;
  }

  public List<String> getStartJoinAddrsWAN() {
    return startJoinAddrsWAN;
  }

  public void setStartJoinAddrsWAN(List<String> startJoinAddrsWAN) {
    this.startJoinAddrsWAN = startJoinAddrsWAN;
  }

  public String getSyncCoordinateIntervalMin() {
    return syncCoordinateIntervalMin;
  }

  public void setSyncCoordinateIntervalMin(String syncCoordinateIntervalMin) {
    this.syncCoordinateIntervalMin = syncCoordinateIntervalMin;
  }

  public int getSyncCoordinateRateTarget() {
    return syncCoordinateRateTarget;
  }

  public void setSyncCoordinateRateTarget(int syncCoordinateRateTarget) {
    this.syncCoordinateRateTarget = syncCoordinateRateTarget;
  }

  public String getSyslogFacility() {
    return syslogFacility;
  }

  public void setSyslogFacility(String syslogFacility) {
    this.syslogFacility = syslogFacility;
  }

  public List<Integer> getTlsCipherSuites() {
    return tlsCipherSuites;
  }

  public void setTlsCipherSuites(List<Integer> tlsCipherSuites) {
    this.tlsCipherSuites = tlsCipherSuites;
  }

  public String getTlsMinVersion() {
    return tlsMinVersion;
  }

  public void setTlsMinVersion(String tlsMinVersion) {
    this.tlsMinVersion = tlsMinVersion;
  }

  public boolean isTlsPreferServerCipherSuites() {
    return tlsPreferServerCipherSuites;
  }

  public void setTlsPreferServerCipherSuites(boolean tlsPreferServerCipherSuites) {
    this.tlsPreferServerCipherSuites = tlsPreferServerCipherSuites;
  }

  public TaggedAddresses getTaggedAddresses() {
    return taggedAddresses;
  }

  public void setTaggedAddresses(TaggedAddresses taggedAddresses) {
    this.taggedAddresses = taggedAddresses;
  }

  public Telemetry getTelemetry() {
    return telemetry;
  }

  public void setTelemetry(Telemetry telemetry) {
    this.telemetry = telemetry;
  }

  public boolean isTranslateWANAddrs() {
    return translateWANAddrs;
  }

  public void setTranslateWANAddrs(boolean translateWANAddrs) {
    this.translateWANAddrs = translateWANAddrs;
  }

  public Optional<String> getUiDir() {
    return uiDir;
  }

  public void setUiDir(Optional<String> uiDir) {
    this.uiDir = uiDir;
  }

  public String getUnixSocketGroup() {
    return unixSocketGroup;
  }

  public void setUnixSocketGroup(String unixSocketGroup) {
    this.unixSocketGroup = unixSocketGroup;
  }

  public String getUnixSocketMode() {
    return unixSocketMode;
  }

  public void setUnixSocketMode(String unixSocketMode) {
    this.unixSocketMode = unixSocketMode;
  }

  public String getUnixSocketUser() {
    return unixSocketUser;
  }

  public void setUnixSocketUser(String unixSocketUser) {
    this.unixSocketUser = unixSocketUser;
  }

  public boolean isVerifyIncoming() {
    return verifyIncoming;
  }

  public void setVerifyIncoming(boolean verifyIncoming) {
    this.verifyIncoming = verifyIncoming;
  }

  public boolean isVerifyIncomingHTTPS() {
    return verifyIncomingHTTPS;
  }

  public void setVerifyIncomingHTTPS(boolean verifyIncomingHTTPS) {
    this.verifyIncomingHTTPS = verifyIncomingHTTPS;
  }

  public boolean isVerifyIncomingRPC() {
    return verifyIncomingRPC;
  }

  public void setVerifyIncomingRPC(boolean verifyIncomingRPC) {
    this.verifyIncomingRPC = verifyIncomingRPC;
  }

  public boolean isVerifyOutgoing() {
    return verifyOutgoing;
  }

  public void setVerifyOutgoing(boolean verifyOutgoing) {
    this.verifyOutgoing = verifyOutgoing;
  }

  public boolean isVerifyServerHostname() {
    return verifyServerHostname;
  }

  public void setVerifyServerHostname(boolean verifyServerHostname) {
    this.verifyServerHostname = verifyServerHostname;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getVersionPrerelease() {
    return versionPrerelease;
  }

  public void setVersionPrerelease(String versionPrerelease) {
    this.versionPrerelease = versionPrerelease;
  }

  public class DNSSOA {

    @JsonProperty("Expire")
    public int expire;

    @JsonProperty("Minttl")
    public int minttl;

    @JsonProperty("Refresh")
    public int refresh;

    @JsonProperty("Retry")
    public int retry;

    public int getExpire() {
      return expire;
    }

    public void setExpire(int expire) {
      this.expire = expire;
    }

    public int getMinttl() {
      return minttl;
    }

    public void setMinttl(int minttl) {
      this.minttl = minttl;
    }

    public int getRefresh() {
      return refresh;
    }

    public void setRefresh(int refresh) {
      this.refresh = refresh;
    }

    public int getRetry() {
      return retry;
    }

    public void setRetry(int retry) {
      this.retry = retry;
    }
  }

  public class NetworkSegment {

    @JsonProperty("Name")
    public String name;

    @JsonProperty("Bind")
    public String bind;

    @JsonProperty("Port")
    public int port;

    @JsonProperty("Advertise")
    public String advertise;

    @JsonProperty("RPCAddr")
    public URI rpcAddr;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getBind() {
      return bind;
    }

    public void setBind(String bind) {
      this.bind = bind;
    }

    public int getPort() {
      return port;
    }

    public void setPort(int port) {
      this.port = port;
    }

    public String getAdvertise() {
      return advertise;
    }

    public void setAdvertise(String advertise) {
      this.advertise = advertise;
    }

    public URI getRpcAddr() {
      return rpcAddr;
    }

    public void setRpcAddr(URI rpcAddr) {
      this.rpcAddr = rpcAddr;
    }
  }

  public class Telemetry {

    @JsonProperty("AllowedPrefixes")
    public List<String> allowedPrefixes;

    @JsonProperty("BlockedPrefixes")
    public List<String> blockedPrefixes;

    @JsonProperty("CirconusAPIApp")
    public String circonusAPIApp;

    @JsonProperty("CirconusAPIToken")
    public String circonusAPIToken;

    @JsonProperty("CirconusAPIURL")
    public String circonusAPIURL;

    @JsonProperty("CirconusBrokerID")
    public String circonusBrokerID;

    @JsonProperty("CirconusBrokerSelectTag")
    public String circonusBrokerSelectTag;

    @JsonProperty("CirconusCheckDisplayName")
    public String circonusCheckDisplayName;

    @JsonProperty("CirconusCheckForceMetricActivation")
    public String circonusCheckForceMetricActivation;

    @JsonProperty("CirconusCheckID")
    public String circonusCheckID;

    @JsonProperty("CirconusCheckInstanceID")
    public String circonusCheckInstanceID;

    @JsonProperty("CirconusCheckSearchTag")
    public String circonusCheckSearchTag;

    @JsonProperty("CirconusCheckTags")
    public String circonusCheckTags;

    @JsonProperty("CirconusSubmissionInterval")
    public String circonusSubmissionInterval;

    @JsonProperty("CirconusSubmissionURL")
    public String circonusSubmissionURL;

    @JsonProperty("DisableHostname")
    public boolean disableHostname;

    @JsonProperty("DogstatsdAddr")
    public String dogstatsdAddr;

    @JsonProperty("DogstatsdTags")
    public List<String> dogstatsdTags;

    @JsonProperty("FilterDefault")
    public boolean filterDefault;

    @JsonProperty("MetricsPrefix")
    public String metricsPrefix;

    @JsonProperty("PrometheusRetentionTime")
    public String prometheusRetentionTime;

    @JsonProperty("StatsdAddr")
    public String statsdAddr;

    @JsonProperty("StatsiteAddr")
    public String statsiteAddr;

    public List<String> getAllowedPrefixes() {
      return allowedPrefixes;
    }

    public void setAllowedPrefixes(List<String> allowedPrefixes) {
      this.allowedPrefixes = allowedPrefixes;
    }

    public List<String> getBlockedPrefixes() {
      return blockedPrefixes;
    }

    public void setBlockedPrefixes(List<String> blockedPrefixes) {
      this.blockedPrefixes = blockedPrefixes;
    }

    public String getCirconusAPIApp() {
      return circonusAPIApp;
    }

    public void setCirconusAPIApp(String circonusAPIApp) {
      this.circonusAPIApp = circonusAPIApp;
    }

    public String getCirconusAPIToken() {
      return circonusAPIToken;
    }

    public void setCirconusAPIToken(String circonusAPIToken) {
      this.circonusAPIToken = circonusAPIToken;
    }

    public String getCirconusAPIURL() {
      return circonusAPIURL;
    }

    public void setCirconusAPIURL(String circonusAPIURL) {
      this.circonusAPIURL = circonusAPIURL;
    }

    public String getCirconusBrokerID() {
      return circonusBrokerID;
    }

    public void setCirconusBrokerID(String circonusBrokerID) {
      this.circonusBrokerID = circonusBrokerID;
    }

    public String getCirconusBrokerSelectTag() {
      return circonusBrokerSelectTag;
    }

    public void setCirconusBrokerSelectTag(String circonusBrokerSelectTag) {
      this.circonusBrokerSelectTag = circonusBrokerSelectTag;
    }

    public String getCirconusCheckDisplayName() {
      return circonusCheckDisplayName;
    }

    public void setCirconusCheckDisplayName(String circonusCheckDisplayName) {
      this.circonusCheckDisplayName = circonusCheckDisplayName;
    }

    public String getCirconusCheckForceMetricActivation() {
      return circonusCheckForceMetricActivation;
    }

    public void setCirconusCheckForceMetricActivation(String circonusCheckForceMetricActivation) {
      this.circonusCheckForceMetricActivation = circonusCheckForceMetricActivation;
    }

    public String getCirconusCheckID() {
      return circonusCheckID;
    }

    public void setCirconusCheckID(String circonusCheckID) {
      this.circonusCheckID = circonusCheckID;
    }

    public String getCirconusCheckInstanceID() {
      return circonusCheckInstanceID;
    }

    public void setCirconusCheckInstanceID(String circonusCheckInstanceID) {
      this.circonusCheckInstanceID = circonusCheckInstanceID;
    }

    public String getCirconusCheckSearchTag() {
      return circonusCheckSearchTag;
    }

    public void setCirconusCheckSearchTag(String circonusCheckSearchTag) {
      this.circonusCheckSearchTag = circonusCheckSearchTag;
    }

    public String getCirconusCheckTags() {
      return circonusCheckTags;
    }

    public void setCirconusCheckTags(String circonusCheckTags) {
      this.circonusCheckTags = circonusCheckTags;
    }

    public String getCirconusSubmissionInterval() {
      return circonusSubmissionInterval;
    }

    public void setCirconusSubmissionInterval(String circonusSubmissionInterval) {
      this.circonusSubmissionInterval = circonusSubmissionInterval;
    }

    public String getCirconusSubmissionURL() {
      return circonusSubmissionURL;
    }

    public void setCirconusSubmissionURL(String circonusSubmissionURL) {
      this.circonusSubmissionURL = circonusSubmissionURL;
    }

    public boolean isDisableHostname() {
      return disableHostname;
    }

    public void setDisableHostname(boolean disableHostname) {
      this.disableHostname = disableHostname;
    }

    public String getDogstatsdAddr() {
      return dogstatsdAddr;
    }

    public void setDogstatsdAddr(String dogstatsdAddr) {
      this.dogstatsdAddr = dogstatsdAddr;
    }

    public List<String> getDogstatsdTags() {
      return dogstatsdTags;
    }

    public void setDogstatsdTags(List<String> dogstatsdTags) {
      this.dogstatsdTags = dogstatsdTags;
    }

    public boolean isFilterDefault() {
      return filterDefault;
    }

    public void setFilterDefault(boolean filterDefault) {
      this.filterDefault = filterDefault;
    }

    public String getMetricsPrefix() {
      return metricsPrefix;
    }

    public void setMetricsPrefix(String metricsPrefix) {
      this.metricsPrefix = metricsPrefix;
    }

    public String getPrometheusRetentionTime() {
      return prometheusRetentionTime;
    }

    public void setPrometheusRetentionTime(String prometheusRetentionTime) {
      this.prometheusRetentionTime = prometheusRetentionTime;
    }

    public String getStatsdAddr() {
      return statsdAddr;
    }

    public void setStatsdAddr(String statsdAddr) {
      this.statsdAddr = statsdAddr;
    }

    public String getStatsiteAddr() {
      return statsiteAddr;
    }

    public void setStatsiteAddr(String statsiteAddr) {
      this.statsiteAddr = statsiteAddr;
    }
  }
}
