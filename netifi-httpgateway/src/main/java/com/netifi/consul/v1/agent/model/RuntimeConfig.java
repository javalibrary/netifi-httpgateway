package com.netifi.consul.v1.agent.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.netifi.consul.v1.catalog.model.TaggedAddresses;
import java.net.URI;
import java.util.List;
import java.util.Map;

// https://github.com/hashicorp/consul/blob/v1.5.1/agent/agent.go#L119
// https://github.com/hashicorp/consul/blob/v1.5.1/agent/config/runtime.go#L28
@JsonDeserialize(builder = RuntimeConfig.Builder.class)
public class RuntimeConfig {

  @JsonProperty("ACLAgentMasterToken")
  private final String aclAgentMasterToken;

  @JsonProperty("ACLAgentToken")
  private final String aclAgentToken;

  @JsonProperty("ACLDatacenter")
  private final String aclDatacenter;

  @JsonProperty("ACLDefaultPolicy")
  private final String aclDefaultPolicy;

  @JsonProperty("ACLDisabledTTL")
  private final String aclDisabledTTL;

  @JsonProperty("ACLDownPolicy")
  private final String aclDownPolicy;

  @JsonProperty("ACLEnableKeyListPolicy")
  private final String aclEnableKeyListPolicy;

  @JsonProperty("ACLEnforceVersion8")
  private final String aclEnforceVersion8;

  @JsonProperty("ACLMasterToken")
  private final String aclMasterToken;

  @JsonProperty("ACLPolicyTTL")
  private final String aclPolicyTTL;

  @JsonProperty("ACLReplicationToken")
  private final String aclReplicationToken;

  @JsonProperty("ACLToken")
  private final String aclToken;

  @JsonProperty("ACLTokenReplication")
  private final boolean aclTokenReplication;

  @JsonProperty("ACLTokenTTL")
  private final String aclTokenTTL;

  @JsonProperty("ACLsEnabled")
  private final boolean aclsEnabled;

  @JsonProperty("AEInterval")
  private final String aeInterval;

  @JsonProperty("AdvertiseAddrLAN")
  private final String advertiseAddrLan;

  @JsonProperty("AdvertiseAddrWAN")
  private final String advertiseAddrWAN;

  @JsonProperty("AutopilotCleanupDeadServers")
  private final boolean autopilotCleanupDeadServers;

  @JsonProperty("AutopilotDisableUpgradeMigration")
  private final boolean autopilotDisableUpgradeMigration;

  @JsonProperty("AutopilotLastContactThreshold")
  private final String autopilotLastContactThreshold;

  @JsonProperty("AutopilotMaxTrailingLogs")
  private final int autopilotMaxTrailingLogs;

  @JsonProperty("AutopilotRedundancyZoneTag")
  private final String autopilotRedundancyZoneTag;

  @JsonProperty("AutopilotServerStabilizationTime")
  private final String autopilotServerStabilizationTime;

  @JsonProperty("AutopilotUpgradeVersionTag")
  private final String autopilotUpgradeVersionTag;

  @JsonProperty("BindAddr")
  private final String bindAddr;

  @JsonProperty("Bootstrap")
  private final boolean bootstrap;

  @JsonProperty("BootstrapExpect")
  private final int bootstrapExpect;

  @JsonProperty("CAFile")
  private final String caFile;

  @JsonProperty("CAPath")
  private final String caPath;

  @JsonProperty("CertFile")
  private final String certFile;

  @JsonProperty("CheckDeregisterIntervalMin")
  private final String checkDeregisterIntervalMin;

  @JsonProperty("CheckReapInterval")
  private final String checkReapInterval;

  @JsonProperty("CheckUpdateInterval")
  private final String checkUpdateInterval;
  // TODO: checks
  @JsonProperty("ClientAddrs")
  private final List<String> clientAddrs;
  // TODO: connectCAConfig
  @JsonProperty("ConnectCAProvider")
  private final boolean connectEnabled;

  @JsonProperty("ConnectProxyAllowManagedAPIRegistration")
  private final boolean connectProxyAllowManagedAPIRegistration;

  @JsonProperty("ConnectProxyAllowManagedRoot")
  private final boolean connectProxyAllowManagedRoot;

  @JsonProperty("ConnectProxyBindMaxPort")
  private final int connectProxyBindMaxPort;

  @JsonProperty("ConnectProxyBindMinPort")
  private final int connectProxyBindMinPort;
  // TODO: connectProxyDefaultConfig
  @JsonProperty("ConnectProxyDefaultDaemonCommand")
  private final List<String> connectProxyDefaultDaemonCommand;

  @JsonProperty("ConnectProxyDefaultExecMode")
  private final String connectProxyDefaultExecMode;

  @JsonProperty("ConnectProxyDefaultScriptCommand")
  private final List<String> connectProxyDefaultScriptCommand;

  @JsonProperty("ConnectReplicationToken")
  private final String connectReplicationToken;

  @JsonProperty("ConnectSidecarMaxPort")
  private final int connectSidecarMaxPort;

  @JsonProperty("ConnectSidecarMinPort")
  private final int connectSidecarMinPort;

  @JsonProperty("ConnectTestDisableManagedProxies")
  private final boolean connectTestDisableManagedProxies;

  @JsonProperty("ConsulCoordinateUpdateBatchSize")
  private final int consulCoordinateUpdateBatchSize;

  @JsonProperty("ConsulCoordinateUpdateMaxBatches")
  private final int consulCoordinateUpdateMaxBatches;

  @JsonProperty("ConsulCoordinateUpdatePeriod")
  private final String consulCoordinateUpdatePeriod;

  @JsonProperty("ConsulRaftElectionTimeout")
  private final String consulRaftElectionTimeout;

  @JsonProperty("ConsulRaftHeartbeatTimeout")
  private final String consulRaftHeartbeatTimeout;

  @JsonProperty("ConsulRaftLeaderLeaseTimeout")
  private final String consulRaftLeaderLeaseTimeout;

  @JsonProperty("ConsulServerHealthInterval")
  private final String consulServerHealthInterval;

  @JsonProperty("DNSARecordLimit")
  private final int dnsARecordLimit;

  @JsonProperty("DNSAddrs")
  private final List<URI> dnsAddrs;

  @JsonProperty("DNSAllowStale")
  private final boolean dnsAllowStale;

  @JsonProperty("DNSDisableCompression")
  private final boolean dnsDisableCompression;

  @JsonProperty("DNSDomain")
  private final String dnsDomain;

  @JsonProperty("DNSEnableTruncate")
  private final boolean dnsEnableTruncate;

  @JsonProperty("DNSMaxStale")
  private final String dnsMaxStale;

  @JsonProperty("DNSNodeMetaTXT")
  private final boolean dnsNodeMetaTXT;

  @JsonProperty("DNSNodeTTL")
  private final String dnsNodeTTL;

  @JsonProperty("DNSOnlyPassing")
  private final boolean dnsOnlyPassing;

  @JsonProperty("DNSPort")
  private final int dnsPort;

  @JsonProperty("DNSRecursorTimeout")
  private final String dnsRecursorTimeout;

  @JsonProperty("DNSRecursors")
  private final List<String> dnsRecursors;

  @JsonProperty("DNSSOA")
  private final DNSSOA dnsSOA;

  @JsonProperty("DNSServiceTTL")
  private final Map<String, String> dnsServiceTTL;

  @JsonProperty("DNSUDPAnswerLimit")
  private final int dnsUDPAnswerLimit;

  @JsonProperty("DataDir")
  private final String dataDir;

  @JsonProperty("Datacenter")
  private final String datacenter;

  @JsonProperty("DevMode")
  private final boolean devMode;

  @JsonProperty("DisableAnonymousSignature")
  private final boolean disableAnonymousSignature;

  @JsonProperty("DisableCoordinates")
  private final boolean disableCoordinates;

  @JsonProperty("DisableHTTPUnprintableCharFilter")
  private final boolean disableHTTPUnprintableCharFilter;

  @JsonProperty("DisableHostNodeID")
  private final boolean disableHostNodeID;

  @JsonProperty("DisableKeyringFile")
  private final boolean disableKeyringFile;

  @JsonProperty("DisableRemoteExec")
  private final boolean disableRemoteExec;

  @JsonProperty("DisableUpdateCheck")
  private final boolean disableUpdateCheck;

  @JsonProperty("DiscardCheckOutput")
  private final boolean discardCheckOutput;

  @JsonProperty("DiscoveryMaxStale")
  private final String discoveryMaxStale;

  @JsonProperty("EnableAgentTLSForChecks")
  private final boolean enableAgentTLSForChecks;

  @JsonProperty("EnableDebug")
  private final boolean enableDebug;

  @JsonProperty("EnableLocalScriptChecks")
  private final boolean enableLocalScriptChecks;

  @JsonProperty("EnableRemoteScriptChecks")
  private final boolean enableRemoteScriptChecks;

  @JsonProperty("EnableSyslog")
  private final boolean enableSyslog;

  @JsonProperty("EnableUI")
  private final boolean enableUI;

  @JsonProperty("EncryptKey")
  private final String encryptKey;

  @JsonProperty("EncryptVerifyIncoming")
  private final boolean encryptVerifyIncoming;

  @JsonProperty("EncryptVerifyOutgoing")
  private final boolean encryptVerifyOutgoing;

  @JsonProperty("GRPCAddrs")
  private final List<URI> grpcAddrs;

  @JsonProperty("GRPCPort")
  private final int grpcPort;

  @JsonProperty("GossipLANGossipInterval")
  private final String gossipLANGossipInterval;

  @JsonProperty("GossipLANGossipNodes")
  private final int gossipLANGossipNodes;

  @JsonProperty("GossipLANProbeInterval")
  private final String gossipLANProbeInterval;

  @JsonProperty("GossipLANProbeTimeout")
  private final String gossipLANProbeTimeout;

  @JsonProperty("GossipLANRetransmitMult")
  private final int gossipLANRetransmitMult;

  @JsonProperty("GossipLANSuspicionMult")
  private final int gossipLANSuspicionMult;

  @JsonProperty("GossipWANGossipInterval")
  private final String gossipWANGossipInterval;

  @JsonProperty("GossipWANGossipNodes")
  private final int gossipWANGossipNodes;

  @JsonProperty("GossipWANProbeInterval")
  private final String gossipWANProbeInterval;

  @JsonProperty("GossipWANProbeTimeout")
  private final String gossipWANProbeTimeout;

  @JsonProperty("GossipWANRetransmitMult")
  private final int gossipWANRetransmitMult;

  @JsonProperty("GossipWANSuspicionMult")
  private final int gossipWANSuspicionMult;

  @JsonProperty("HTTPAddrs")
  private final List<URI> httpAddrs;

  @JsonProperty("HTTPBlockEndpoints")
  private final List<String> httpBlockEndpoints;

  @JsonProperty("HTTPPort")
  private final int httpPort;

  @JsonProperty("HTTPResponseHeaders")
  private final Map<String, String> httpResponseHeaders;

  @JsonProperty("HTTPSAddrs")
  private final List<URI> httpsAddrs;

  @JsonProperty("HTTPSPort")
  private final int httpsPort;

  @JsonProperty("KeyFile")
  private final String keyFile;

  @JsonProperty("LeaveDrainTime")
  private final String leaveDrainTime;

  @JsonProperty("LeaveOnTerm")
  private final boolean leaveOnTerm;

  @JsonProperty("LogFile")
  private final String logFile;

  @JsonProperty("LogLevel")
  private final String logLevel;

  @JsonProperty("LogRotateBytes")
  private final int logRotateBytes;

  @JsonProperty("LogRotateDuration")
  private final String logRotateDuration;

  @JsonProperty("NodeID")
  private final String nodeID;

  @JsonProperty("NodeMeta")
  private final Map<String, String> nodeMeta;

  @JsonProperty("NodeName")
  private final String nodeName;

  @JsonProperty("NonVotingServer")
  private final boolean nonVotingServer;

  @JsonProperty("PidFile")
  private final String pidFile;

  @JsonProperty("PrimaryDatacenter")
  private final String primaryDatacenter;

  @JsonProperty("RPCAdvertiseAddr")
  private final URI rpcAdvertiseAddr;

  @JsonProperty("RPCBindAddr")
  private final URI rpcBindAddr;

  @JsonProperty("RPCHoldTimeout")
  private final String rpcHoldTimeout;

  @JsonProperty("RPCMaxBurst")
  private final int rpcMaxBurst;

  @JsonProperty("RPCProtocol")
  private final int rpcProtocol;

  @JsonProperty("RPCRateLimit")
  private final int rpcRateLimit;

  @JsonProperty("RaftProtocol")
  private final int raftProtocol;

  @JsonProperty("RaftSnapshotInterval")
  private final String raftSnapshotInterval;

  @JsonProperty("RaftSnapshotThreshold")
  private final int raftSnapshotThreshold;

  @JsonProperty("ReconnectTimeoutLAN")
  private final String reconnectTimeoutLAN;

  @JsonProperty("ReconnectTimeoutWAN")
  private final String reconnectTimeoutWAN;

  @JsonProperty("RejoinAfterLeave")
  private final boolean rejoinAfterLeave;

  @JsonProperty("RetryJoinIntervalLAN")
  private final String retryJoinIntervalLAN;

  @JsonProperty("RetryJoinIntervalWAN")
  private final String retryJoinIntervalWAN;

  @JsonProperty("RetryJoinLAN")
  private final List<String> retryJoinLAN;

  @JsonProperty("RetryJoinMaxAttemptsLAN")
  private final int retryJoinMaxAttemptsLAN;

  @JsonProperty("RetryJoinMaxAttemptsWAN")
  private final int retryJoinMaxAttemptsWAN;

  @JsonProperty("RetryJoinWAN")
  private final List<String> retryJoinWAN;

  @JsonProperty("Revision")
  private final String revision;

  @JsonProperty("SegmentLimit")
  private final int segmentLimit;

  @JsonProperty("SegmentName")
  private final String segmentName;

  @JsonProperty("SegmentNameLimit")
  private final int segmentNameLimit;

  @JsonProperty("Segments")
  private final List<NetworkSegment> segments;

  @JsonProperty("SerfAdvertiseAddrLAN")
  private final URI serfAdvertiseAddrLAN;

  @JsonProperty("SerfAdvertiseAddrWAN")
  private final URI serfAdvertiseAddrWAN;

  @JsonProperty("SerfBindAddrLAN")
  private final URI serfBindAddrLAN;

  @JsonProperty("SerfBindAddrWAN")
  private final URI serfBindAddrWAN;

  @JsonProperty("SerfPortLAN")
  private final int serfPortLAN;

  @JsonProperty("SerfPortWAN")
  private final int serfPortWAN;

  @JsonProperty("ServerMode")
  private final boolean serverMode;

  @JsonProperty("ServerName")
  private final String serverName;

  @JsonProperty("ServerPort")
  private final int serverPort;
  // TODO: Services
  @JsonProperty("SessionTTLMin")
  private final String sessionTTLMin;

  @JsonProperty("SkipLeaveOnInt")
  private final boolean skipLeaveOnInt;

  @JsonProperty("StartJoinAddrsLAN")
  private final List<String> startJoinAddrsLAN;

  @JsonProperty("StartJoinAddrsWAN")
  private final List<String> startJoinAddrsWAN;

  @JsonProperty("SyncCoordinateIntervalMin")
  private final String syncCoordinateIntervalMin;

  @JsonProperty("SyncCoordinateRateTarget")
  private final int syncCoordinateRateTarget;

  @JsonProperty("SyslogFacility")
  private final String syslogFacility;

  @JsonProperty("TLSCipherSuites")
  private final List<Integer> tlsCipherSuites;

  @JsonProperty("TLSMinVersion")
  private final String tlsMinVersion;

  @JsonProperty("TLSPreferServerCipherSuites")
  private final boolean tlsPreferServerCipherSuites;

  @JsonProperty("TaggedAddresses")
  private final TaggedAddresses taggedAddresses;

  @JsonProperty("Telemetry")
  private final Telemetry telemetry;

  @JsonProperty("TranslateWANAddrs")
  private final boolean translateWANAddrs;

  @JsonProperty("UIDir")
  private final String uiDir;

  @JsonProperty("UnixSocketGroup")
  private final String unixSocketGroup;

  @JsonProperty("UnixSocketMode")
  private final String unixSocketMode;

  @JsonProperty("UnixSocketUser")
  private final String unixSocketUser;

  @JsonProperty("VerifyIncoming")
  private final boolean verifyIncoming;

  @JsonProperty("VerifyIncomingHTTPS")
  private final boolean verifyIncomingHTTPS;

  @JsonProperty("VerifyIncomingRPC")
  private final boolean verifyIncomingRPC;

  @JsonProperty("VerifyOutgoing")
  private final boolean verifyOutgoing;

  @JsonProperty("VerifyServerHostname")
  private final boolean verifyServerHostname;

  @JsonProperty("Version")
  private final String version;

  @JsonProperty("VersionPrerelease")
  private final String versionPrerelease;

  // TODO: Watches

  private RuntimeConfig(Builder builder) {
    aclAgentMasterToken = builder.aclAgentMasterToken;
    aclAgentToken = builder.aclAgentToken;
    aclDatacenter = builder.aclDatacenter;
    aclDefaultPolicy = builder.aclDefaultPolicy;
    aclDisabledTTL = builder.aclDisabledTTL;
    aclDownPolicy = builder.aclDownPolicy;
    aclEnableKeyListPolicy = builder.aclEnableKeyListPolicy;
    aclEnforceVersion8 = builder.aclEnforceVersion8;
    aclMasterToken = builder.aclMasterToken;
    aclPolicyTTL = builder.aclPolicyTTL;
    aclReplicationToken = builder.aclReplicationToken;
    aclToken = builder.aclToken;
    aclTokenReplication = builder.aclTokenReplication;
    aclTokenTTL = builder.aclTokenTTL;
    aclsEnabled = builder.aclsEnabled;
    aeInterval = builder.aeInterval;
    advertiseAddrLan = builder.advertiseAddrLan;
    advertiseAddrWAN = builder.advertiseAddrWAN;
    autopilotCleanupDeadServers = builder.autopilotCleanupDeadServers;
    autopilotDisableUpgradeMigration = builder.autopilotDisableUpgradeMigration;
    autopilotLastContactThreshold = builder.autopilotLastContactThreshold;
    autopilotMaxTrailingLogs = builder.autopilotMaxTrailingLogs;
    autopilotRedundancyZoneTag = builder.autopilotRedundancyZoneTag;
    autopilotServerStabilizationTime = builder.autopilotServerStabilizationTime;
    autopilotUpgradeVersionTag = builder.autopilotUpgradeVersionTag;
    bindAddr = builder.bindAddr;
    bootstrap = builder.bootstrap;
    bootstrapExpect = builder.bootstrapExpect;
    caFile = builder.caFile;
    caPath = builder.caPath;
    certFile = builder.certFile;
    checkDeregisterIntervalMin = builder.checkDeregisterIntervalMin;
    checkReapInterval = builder.checkReapInterval;
    checkUpdateInterval = builder.checkUpdateInterval;
    clientAddrs = builder.clientAddrs;
    connectEnabled = builder.connectEnabled;
    connectProxyAllowManagedAPIRegistration = builder.connectProxyAllowManagedAPIRegistration;
    connectProxyAllowManagedRoot = builder.connectProxyAllowManagedRoot;
    connectProxyBindMaxPort = builder.connectProxyBindMaxPort;
    connectProxyBindMinPort = builder.connectProxyBindMinPort;
    connectProxyDefaultDaemonCommand = builder.connectProxyDefaultDaemonCommand;
    connectProxyDefaultExecMode = builder.connectProxyDefaultExecMode;
    connectProxyDefaultScriptCommand = builder.connectProxyDefaultScriptCommand;
    connectReplicationToken = builder.connectReplicationToken;
    connectSidecarMaxPort = builder.connectSidecarMaxPort;
    connectSidecarMinPort = builder.connectSidecarMinPort;
    connectTestDisableManagedProxies = builder.connectTestDisableManagedProxies;
    consulCoordinateUpdateBatchSize = builder.consulCoordinateUpdateBatchSize;
    consulCoordinateUpdateMaxBatches = builder.consulCoordinateUpdateMaxBatches;
    consulCoordinateUpdatePeriod = builder.consulCoordinateUpdatePeriod;
    consulRaftElectionTimeout = builder.consulRaftElectionTimeout;
    consulRaftHeartbeatTimeout = builder.consulRaftHeartbeatTimeout;
    consulRaftLeaderLeaseTimeout = builder.consulRaftLeaderLeaseTimeout;
    consulServerHealthInterval = builder.consulServerHealthInterval;
    dnsARecordLimit = builder.dnsARecordLimit;
    dnsAddrs = builder.dnsAddrs;
    dnsAllowStale = builder.dnsAllowStale;
    dnsDisableCompression = builder.dnsDisableCompression;
    dnsDomain = builder.dnsDomain;
    dnsEnableTruncate = builder.dnsEnableTruncate;
    dnsMaxStale = builder.dnsMaxStale;
    dnsNodeMetaTXT = builder.dnsNodeMetaTXT;
    dnsNodeTTL = builder.dnsNodeTTL;
    dnsOnlyPassing = builder.dnsOnlyPassing;
    dnsPort = builder.dnsPort;
    dnsRecursorTimeout = builder.dnsRecursorTimeout;
    dnsRecursors = builder.dnsRecursors;
    dnsSOA = builder.dnsSOA;
    dnsServiceTTL = builder.dnsServiceTTL;
    dnsUDPAnswerLimit = builder.dnsUDPAnswerLimit;
    dataDir = builder.dataDir;
    datacenter = builder.datacenter;
    devMode = builder.devMode;
    disableAnonymousSignature = builder.disableAnonymousSignature;
    disableCoordinates = builder.disableCoordinates;
    disableHTTPUnprintableCharFilter = builder.disableHTTPUnprintableCharFilter;
    disableHostNodeID = builder.disableHostNodeID;
    disableKeyringFile = builder.disableKeyringFile;
    disableRemoteExec = builder.disableRemoteExec;
    disableUpdateCheck = builder.disableUpdateCheck;
    discardCheckOutput = builder.discardCheckOutput;
    discoveryMaxStale = builder.discoveryMaxStale;
    enableAgentTLSForChecks = builder.enableAgentTLSForChecks;
    enableDebug = builder.enableDebug;
    enableLocalScriptChecks = builder.enableLocalScriptChecks;
    enableRemoteScriptChecks = builder.enableRemoteScriptChecks;
    enableSyslog = builder.enableSyslog;
    enableUI = builder.enableUI;
    encryptKey = builder.encryptKey;
    encryptVerifyIncoming = builder.encryptVerifyIncoming;
    encryptVerifyOutgoing = builder.encryptVerifyOutgoing;
    grpcAddrs = builder.grpcAddrs;
    grpcPort = builder.grpcPort;
    gossipLANGossipInterval = builder.gossipLANGossipInterval;
    gossipLANGossipNodes = builder.gossipLANGossipNodes;
    gossipLANProbeInterval = builder.gossipLANProbeInterval;
    gossipLANProbeTimeout = builder.gossipLANProbeTimeout;
    gossipLANRetransmitMult = builder.gossipLANRetransmitMult;
    gossipLANSuspicionMult = builder.gossipLANSuspicionMult;
    gossipWANGossipInterval = builder.gossipWANGossipInterval;
    gossipWANGossipNodes = builder.gossipWANGossipNodes;
    gossipWANProbeInterval = builder.gossipWANProbeInterval;
    gossipWANProbeTimeout = builder.gossipWANProbeTimeout;
    gossipWANRetransmitMult = builder.gossipWANRetransmitMult;
    gossipWANSuspicionMult = builder.gossipWANSuspicionMult;
    httpAddrs = builder.httpAddrs;
    httpBlockEndpoints = builder.httpBlockEndpoints;
    httpPort = builder.httpPort;
    httpResponseHeaders = builder.httpResponseHeaders;
    httpsAddrs = builder.httpsAddrs;
    httpsPort = builder.httpsPort;
    keyFile = builder.keyFile;
    leaveDrainTime = builder.leaveDrainTime;
    leaveOnTerm = builder.leaveOnTerm;
    logFile = builder.logFile;
    logLevel = builder.logLevel;
    logRotateBytes = builder.logRotateBytes;
    logRotateDuration = builder.logRotateDuration;
    nodeID = builder.nodeID;
    nodeMeta = builder.nodeMeta;
    nodeName = builder.nodeName;
    nonVotingServer = builder.nonVotingServer;
    pidFile = builder.pidFile;
    primaryDatacenter = builder.primaryDatacenter;
    rpcAdvertiseAddr = builder.rpcAdvertiseAddr;
    rpcBindAddr = builder.rpcBindAddr;
    rpcHoldTimeout = builder.rpcHoldTimeout;
    rpcMaxBurst = builder.rpcMaxBurst;
    rpcProtocol = builder.rpcProtocol;
    rpcRateLimit = builder.rpcRateLimit;
    raftProtocol = builder.raftProtocol;
    raftSnapshotInterval = builder.raftSnapshotInterval;
    raftSnapshotThreshold = builder.raftSnapshotThreshold;
    reconnectTimeoutLAN = builder.reconnectTimeoutLAN;
    reconnectTimeoutWAN = builder.reconnectTimeoutWAN;
    rejoinAfterLeave = builder.rejoinAfterLeave;
    retryJoinIntervalLAN = builder.retryJoinIntervalLAN;
    retryJoinIntervalWAN = builder.retryJoinIntervalWAN;
    retryJoinLAN = builder.retryJoinLAN;
    retryJoinMaxAttemptsLAN = builder.retryJoinMaxAttemptsLAN;
    retryJoinMaxAttemptsWAN = builder.retryJoinMaxAttemptsWAN;
    retryJoinWAN = builder.retryJoinWAN;
    revision = builder.revision;
    segmentLimit = builder.segmentLimit;
    segmentName = builder.segmentName;
    segmentNameLimit = builder.segmentNameLimit;
    segments = builder.segments;
    serfAdvertiseAddrLAN = builder.serfAdvertiseAddrLAN;
    serfAdvertiseAddrWAN = builder.serfAdvertiseAddrWAN;
    serfBindAddrLAN = builder.serfBindAddrLAN;
    serfBindAddrWAN = builder.serfBindAddrWAN;
    serfPortLAN = builder.serfPortLAN;
    serfPortWAN = builder.serfPortWAN;
    serverMode = builder.serverMode;
    serverName = builder.serverName;
    serverPort = builder.serverPort;
    sessionTTLMin = builder.sessionTTLMin;
    skipLeaveOnInt = builder.skipLeaveOnInt;
    startJoinAddrsLAN = builder.startJoinAddrsLAN;
    startJoinAddrsWAN = builder.startJoinAddrsWAN;
    syncCoordinateIntervalMin = builder.syncCoordinateIntervalMin;
    syncCoordinateRateTarget = builder.syncCoordinateRateTarget;
    syslogFacility = builder.syslogFacility;
    tlsCipherSuites = builder.tlsCipherSuites;
    tlsMinVersion = builder.tlsMinVersion;
    tlsPreferServerCipherSuites = builder.tlsPreferServerCipherSuites;
    taggedAddresses = builder.taggedAddresses;
    telemetry = builder.telemetry;
    translateWANAddrs = builder.translateWANAddrs;
    uiDir = builder.uiDir;
    unixSocketGroup = builder.unixSocketGroup;
    unixSocketMode = builder.unixSocketMode;
    unixSocketUser = builder.unixSocketUser;
    verifyIncoming = builder.verifyIncoming;
    verifyIncomingHTTPS = builder.verifyIncomingHTTPS;
    verifyIncomingRPC = builder.verifyIncomingRPC;
    verifyOutgoing = builder.verifyOutgoing;
    verifyServerHostname = builder.verifyServerHostname;
    version = builder.version;
    versionPrerelease = builder.versionPrerelease;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getAclAgentMasterToken() {
    return aclAgentMasterToken;
  }

  public String getAclAgentToken() {
    return aclAgentToken;
  }

  public String getAclDatacenter() {
    return aclDatacenter;
  }

  public String getAclDefaultPolicy() {
    return aclDefaultPolicy;
  }

  public String getAclDisabledTTL() {
    return aclDisabledTTL;
  }

  public String getAclDownPolicy() {
    return aclDownPolicy;
  }

  public String getAclEnableKeyListPolicy() {
    return aclEnableKeyListPolicy;
  }

  public String getAclEnforceVersion8() {
    return aclEnforceVersion8;
  }

  public String getAclMasterToken() {
    return aclMasterToken;
  }

  public String getAclPolicyTTL() {
    return aclPolicyTTL;
  }

  public String getAclReplicationToken() {
    return aclReplicationToken;
  }

  public String getAclToken() {
    return aclToken;
  }

  public boolean isAclTokenReplication() {
    return aclTokenReplication;
  }

  public String getAclTokenTTL() {
    return aclTokenTTL;
  }

  public boolean isAclsEnabled() {
    return aclsEnabled;
  }

  public String getAeInterval() {
    return aeInterval;
  }

  public String getAdvertiseAddrLan() {
    return advertiseAddrLan;
  }

  public String getAdvertiseAddrWAN() {
    return advertiseAddrWAN;
  }

  public boolean isAutopilotCleanupDeadServers() {
    return autopilotCleanupDeadServers;
  }

  public boolean isAutopilotDisableUpgradeMigration() {
    return autopilotDisableUpgradeMigration;
  }

  public String getAutopilotLastContactThreshold() {
    return autopilotLastContactThreshold;
  }

  public int getAutopilotMaxTrailingLogs() {
    return autopilotMaxTrailingLogs;
  }

  public String getAutopilotRedundancyZoneTag() {
    return autopilotRedundancyZoneTag;
  }

  public String getAutopilotServerStabilizationTime() {
    return autopilotServerStabilizationTime;
  }

  public String getAutopilotUpgradeVersionTag() {
    return autopilotUpgradeVersionTag;
  }

  public String getBindAddr() {
    return bindAddr;
  }

  public boolean isBootstrap() {
    return bootstrap;
  }

  public int getBootstrapExpect() {
    return bootstrapExpect;
  }

  public String getCaFile() {
    return caFile;
  }

  public String getCaPath() {
    return caPath;
  }

  public String getCertFile() {
    return certFile;
  }

  public String getCheckDeregisterIntervalMin() {
    return checkDeregisterIntervalMin;
  }

  public String getCheckReapInterval() {
    return checkReapInterval;
  }

  public String getCheckUpdateInterval() {
    return checkUpdateInterval;
  }

  public List<String> getClientAddrs() {
    return clientAddrs;
  }

  public boolean isConnectEnabled() {
    return connectEnabled;
  }

  public boolean isConnectProxyAllowManagedAPIRegistration() {
    return connectProxyAllowManagedAPIRegistration;
  }

  public boolean isConnectProxyAllowManagedRoot() {
    return connectProxyAllowManagedRoot;
  }

  public int getConnectProxyBindMaxPort() {
    return connectProxyBindMaxPort;
  }

  public int getConnectProxyBindMinPort() {
    return connectProxyBindMinPort;
  }

  public List<String> getConnectProxyDefaultDaemonCommand() {
    return connectProxyDefaultDaemonCommand;
  }

  public String getConnectProxyDefaultExecMode() {
    return connectProxyDefaultExecMode;
  }

  public List<String> getConnectProxyDefaultScriptCommand() {
    return connectProxyDefaultScriptCommand;
  }

  public String getConnectReplicationToken() {
    return connectReplicationToken;
  }

  public int getConnectSidecarMaxPort() {
    return connectSidecarMaxPort;
  }

  public int getConnectSidecarMinPort() {
    return connectSidecarMinPort;
  }

  public boolean isConnectTestDisableManagedProxies() {
    return connectTestDisableManagedProxies;
  }

  public int getConsulCoordinateUpdateBatchSize() {
    return consulCoordinateUpdateBatchSize;
  }

  public int getConsulCoordinateUpdateMaxBatches() {
    return consulCoordinateUpdateMaxBatches;
  }

  public String getConsulCoordinateUpdatePeriod() {
    return consulCoordinateUpdatePeriod;
  }

  public String getConsulRaftElectionTimeout() {
    return consulRaftElectionTimeout;
  }

  public String getConsulRaftHeartbeatTimeout() {
    return consulRaftHeartbeatTimeout;
  }

  public String getConsulRaftLeaderLeaseTimeout() {
    return consulRaftLeaderLeaseTimeout;
  }

  public String getConsulServerHealthInterval() {
    return consulServerHealthInterval;
  }

  public int getDnsARecordLimit() {
    return dnsARecordLimit;
  }

  public List<URI> getDnsAddrs() {
    return dnsAddrs;
  }

  public boolean isDnsAllowStale() {
    return dnsAllowStale;
  }

  public boolean isDnsDisableCompression() {
    return dnsDisableCompression;
  }

  public String getDnsDomain() {
    return dnsDomain;
  }

  public boolean isDnsEnableTruncate() {
    return dnsEnableTruncate;
  }

  public String getDnsMaxStale() {
    return dnsMaxStale;
  }

  public boolean isDnsNodeMetaTXT() {
    return dnsNodeMetaTXT;
  }

  public String getDnsNodeTTL() {
    return dnsNodeTTL;
  }

  public boolean isDnsOnlyPassing() {
    return dnsOnlyPassing;
  }

  public int getDnsPort() {
    return dnsPort;
  }

  public String getDnsRecursorTimeout() {
    return dnsRecursorTimeout;
  }

  public List<String> getDnsRecursors() {
    return dnsRecursors;
  }

  public DNSSOA getDnsSOA() {
    return dnsSOA;
  }

  public Map<String, String> getDnsServiceTTL() {
    return dnsServiceTTL;
  }

  public int getDnsUDPAnswerLimit() {
    return dnsUDPAnswerLimit;
  }

  public String getDataDir() {
    return dataDir;
  }

  public String getDatacenter() {
    return datacenter;
  }

  public boolean isDevMode() {
    return devMode;
  }

  public boolean isDisableAnonymousSignature() {
    return disableAnonymousSignature;
  }

  public boolean isDisableCoordinates() {
    return disableCoordinates;
  }

  public boolean isDisableHTTPUnprintableCharFilter() {
    return disableHTTPUnprintableCharFilter;
  }

  public boolean isDisableHostNodeID() {
    return disableHostNodeID;
  }

  public boolean isDisableKeyringFile() {
    return disableKeyringFile;
  }

  public boolean isDisableRemoteExec() {
    return disableRemoteExec;
  }

  public boolean isDisableUpdateCheck() {
    return disableUpdateCheck;
  }

  public boolean isDiscardCheckOutput() {
    return discardCheckOutput;
  }

  public String getDiscoveryMaxStale() {
    return discoveryMaxStale;
  }

  public boolean isEnableAgentTLSForChecks() {
    return enableAgentTLSForChecks;
  }

  public boolean isEnableDebug() {
    return enableDebug;
  }

  public boolean isEnableLocalScriptChecks() {
    return enableLocalScriptChecks;
  }

  public boolean isEnableRemoteScriptChecks() {
    return enableRemoteScriptChecks;
  }

  public boolean isEnableSyslog() {
    return enableSyslog;
  }

  public boolean isEnableUI() {
    return enableUI;
  }

  public String getEncryptKey() {
    return encryptKey;
  }

  public boolean isEncryptVerifyIncoming() {
    return encryptVerifyIncoming;
  }

  public boolean isEncryptVerifyOutgoing() {
    return encryptVerifyOutgoing;
  }

  public List<URI> getGrpcAddrs() {
    return grpcAddrs;
  }

  public int getGrpcPort() {
    return grpcPort;
  }

  public String getGossipLANGossipInterval() {
    return gossipLANGossipInterval;
  }

  public int getGossipLANGossipNodes() {
    return gossipLANGossipNodes;
  }

  public String getGossipLANProbeInterval() {
    return gossipLANProbeInterval;
  }

  public String getGossipLANProbeTimeout() {
    return gossipLANProbeTimeout;
  }

  public int getGossipLANRetransmitMult() {
    return gossipLANRetransmitMult;
  }

  public int getGossipLANSuspicionMult() {
    return gossipLANSuspicionMult;
  }

  public String getGossipWANGossipInterval() {
    return gossipWANGossipInterval;
  }

  public int getGossipWANGossipNodes() {
    return gossipWANGossipNodes;
  }

  public String getGossipWANProbeInterval() {
    return gossipWANProbeInterval;
  }

  public String getGossipWANProbeTimeout() {
    return gossipWANProbeTimeout;
  }

  public int getGossipWANRetransmitMult() {
    return gossipWANRetransmitMult;
  }

  public int getGossipWANSuspicionMult() {
    return gossipWANSuspicionMult;
  }

  public List<URI> getHttpAddrs() {
    return httpAddrs;
  }

  public List<String> getHttpBlockEndpoints() {
    return httpBlockEndpoints;
  }

  public int getHttpPort() {
    return httpPort;
  }

  public Map<String, String> getHttpResponseHeaders() {
    return httpResponseHeaders;
  }

  public List<URI> getHttpsAddrs() {
    return httpsAddrs;
  }

  public int getHttpsPort() {
    return httpsPort;
  }

  public String getKeyFile() {
    return keyFile;
  }

  public String getLeaveDrainTime() {
    return leaveDrainTime;
  }

  public boolean isLeaveOnTerm() {
    return leaveOnTerm;
  }

  public String getLogFile() {
    return logFile;
  }

  public String getLogLevel() {
    return logLevel;
  }

  public int getLogRotateBytes() {
    return logRotateBytes;
  }

  public String getLogRotateDuration() {
    return logRotateDuration;
  }

  public String getNodeID() {
    return nodeID;
  }

  public Map<String, String> getNodeMeta() {
    return nodeMeta;
  }

  public String getNodeName() {
    return nodeName;
  }

  public boolean isNonVotingServer() {
    return nonVotingServer;
  }

  public String getPidFile() {
    return pidFile;
  }

  public String getPrimaryDatacenter() {
    return primaryDatacenter;
  }

  public URI getRpcAdvertiseAddr() {
    return rpcAdvertiseAddr;
  }

  public URI getRpcBindAddr() {
    return rpcBindAddr;
  }

  public String getRpcHoldTimeout() {
    return rpcHoldTimeout;
  }

  public int getRpcMaxBurst() {
    return rpcMaxBurst;
  }

  public int getRpcProtocol() {
    return rpcProtocol;
  }

  public int getRpcRateLimit() {
    return rpcRateLimit;
  }

  public int getRaftProtocol() {
    return raftProtocol;
  }

  public String getRaftSnapshotInterval() {
    return raftSnapshotInterval;
  }

  public int getRaftSnapshotThreshold() {
    return raftSnapshotThreshold;
  }

  public String getReconnectTimeoutLAN() {
    return reconnectTimeoutLAN;
  }

  public String getReconnectTimeoutWAN() {
    return reconnectTimeoutWAN;
  }

  public boolean isRejoinAfterLeave() {
    return rejoinAfterLeave;
  }

  public String getRetryJoinIntervalLAN() {
    return retryJoinIntervalLAN;
  }

  public String getRetryJoinIntervalWAN() {
    return retryJoinIntervalWAN;
  }

  public List<String> getRetryJoinLAN() {
    return retryJoinLAN;
  }

  public int getRetryJoinMaxAttemptsLAN() {
    return retryJoinMaxAttemptsLAN;
  }

  public int getRetryJoinMaxAttemptsWAN() {
    return retryJoinMaxAttemptsWAN;
  }

  public List<String> getRetryJoinWAN() {
    return retryJoinWAN;
  }

  public String getRevision() {
    return revision;
  }

  public int getSegmentLimit() {
    return segmentLimit;
  }

  public String getSegmentName() {
    return segmentName;
  }

  public int getSegmentNameLimit() {
    return segmentNameLimit;
  }

  public List<NetworkSegment> getSegments() {
    return segments;
  }

  public URI getSerfAdvertiseAddrLAN() {
    return serfAdvertiseAddrLAN;
  }

  public URI getSerfAdvertiseAddrWAN() {
    return serfAdvertiseAddrWAN;
  }

  public URI getSerfBindAddrLAN() {
    return serfBindAddrLAN;
  }

  public URI getSerfBindAddrWAN() {
    return serfBindAddrWAN;
  }

  public int getSerfPortLAN() {
    return serfPortLAN;
  }

  public int getSerfPortWAN() {
    return serfPortWAN;
  }

  public boolean isServerMode() {
    return serverMode;
  }

  public String getServerName() {
    return serverName;
  }

  public int getServerPort() {
    return serverPort;
  }

  public String getSessionTTLMin() {
    return sessionTTLMin;
  }

  public boolean isSkipLeaveOnInt() {
    return skipLeaveOnInt;
  }

  public List<String> getStartJoinAddrsLAN() {
    return startJoinAddrsLAN;
  }

  public List<String> getStartJoinAddrsWAN() {
    return startJoinAddrsWAN;
  }

  public String getSyncCoordinateIntervalMin() {
    return syncCoordinateIntervalMin;
  }

  public int getSyncCoordinateRateTarget() {
    return syncCoordinateRateTarget;
  }

  public String getSyslogFacility() {
    return syslogFacility;
  }

  public List<Integer> getTlsCipherSuites() {
    return tlsCipherSuites;
  }

  public String getTlsMinVersion() {
    return tlsMinVersion;
  }

  public boolean isTlsPreferServerCipherSuites() {
    return tlsPreferServerCipherSuites;
  }

  public TaggedAddresses getTaggedAddresses() {
    return taggedAddresses;
  }

  public Telemetry getTelemetry() {
    return telemetry;
  }

  public boolean isTranslateWANAddrs() {
    return translateWANAddrs;
  }

  public String getUiDir() {
    return uiDir;
  }

  public String getUnixSocketGroup() {
    return unixSocketGroup;
  }

  public String getUnixSocketMode() {
    return unixSocketMode;
  }

  public String getUnixSocketUser() {
    return unixSocketUser;
  }

  public boolean isVerifyIncoming() {
    return verifyIncoming;
  }

  public boolean isVerifyIncomingHTTPS() {
    return verifyIncomingHTTPS;
  }

  public boolean isVerifyIncomingRPC() {
    return verifyIncomingRPC;
  }

  public boolean isVerifyOutgoing() {
    return verifyOutgoing;
  }

  public boolean isVerifyServerHostname() {
    return verifyServerHostname;
  }

  public String getVersion() {
    return version;
  }

  public String getVersionPrerelease() {
    return versionPrerelease;
  }

  @JsonPOJOBuilder
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static final class Builder {

    @JsonProperty("ACLAgentMasterToken")
    private String aclAgentMasterToken;

    @JsonProperty("ACLAgentToken")
    private String aclAgentToken;

    @JsonProperty("ACLDatacenter")
    private String aclDatacenter;

    @JsonProperty("ACLDefaultPolicy")
    private String aclDefaultPolicy;

    @JsonProperty("ACLDisabledTTL")
    private String aclDisabledTTL;

    @JsonProperty("ACLDownPolicy")
    private String aclDownPolicy;

    @JsonProperty("ACLEnableKeyListPolicy")
    private String aclEnableKeyListPolicy;

    @JsonProperty("ACLEnforceVersion8")
    private String aclEnforceVersion8;

    @JsonProperty("ACLMasterToken")
    private String aclMasterToken;

    @JsonProperty("ACLPolicyTTL")
    private String aclPolicyTTL;

    @JsonProperty("ACLReplicationToken")
    private String aclReplicationToken;

    @JsonProperty("ACLToken")
    private String aclToken;

    @JsonProperty("ACLTokenReplication")
    private boolean aclTokenReplication;

    @JsonProperty("ACLTokenTTL")
    private String aclTokenTTL;

    @JsonProperty("ACLsEnabled")
    private boolean aclsEnabled;

    @JsonProperty("AEInterval")
    private String aeInterval;

    @JsonProperty("AdvertiseAddrLAN")
    private String advertiseAddrLan;

    @JsonProperty("AdvertiseAddrWAN")
    private String advertiseAddrWAN;

    @JsonProperty("AutopilotCleanupDeadServers")
    private boolean autopilotCleanupDeadServers;

    @JsonProperty("AutopilotDisableUpgradeMigration")
    private boolean autopilotDisableUpgradeMigration;

    @JsonProperty("AutopilotLastContactThreshold")
    private String autopilotLastContactThreshold;

    @JsonProperty("AutopilotMaxTrailingLogs")
    private int autopilotMaxTrailingLogs;

    @JsonProperty("AutopilotRedundancyZoneTag")
    private String autopilotRedundancyZoneTag;

    @JsonProperty("AutopilotServerStabilizationTime")
    private String autopilotServerStabilizationTime;

    @JsonProperty("AutopilotUpgradeVersionTag")
    private String autopilotUpgradeVersionTag;

    @JsonProperty("BindAddr")
    private String bindAddr;

    @JsonProperty("Bootstrap")
    private boolean bootstrap;

    @JsonProperty("BootstrapExpect")
    private int bootstrapExpect;

    @JsonProperty("CAFile")
    private String caFile;

    @JsonProperty("CAPath")
    private String caPath;

    @JsonProperty("CertFile")
    private String certFile;

    @JsonProperty("CheckDeregisterIntervalMin")
    private String checkDeregisterIntervalMin;

    @JsonProperty("CheckReapInterval")
    private String checkReapInterval;

    @JsonProperty("CheckUpdateInterval")
    private String checkUpdateInterval;
    // TODO: checks
    @JsonProperty("ClientAddrs")
    private List<String> clientAddrs;
    // TODO: connectCAConfig
    @JsonProperty("ConnectCAProvider")
    private boolean connectEnabled;

    @JsonProperty("ConnectProxyAllowManagedAPIRegistration")
    private boolean connectProxyAllowManagedAPIRegistration;

    @JsonProperty("ConnectProxyAllowManagedRoot")
    private boolean connectProxyAllowManagedRoot;

    @JsonProperty("ConnectProxyBindMaxPort")
    private int connectProxyBindMaxPort;

    @JsonProperty("ConnectProxyBindMinPort")
    private int connectProxyBindMinPort;
    // TODO: connectProxyDefaultConfig
    @JsonProperty("ConnectProxyDefaultDaemonCommand")
    private List<String> connectProxyDefaultDaemonCommand;

    @JsonProperty("ConnectProxyDefaultExecMode")
    private String connectProxyDefaultExecMode;

    @JsonProperty("ConnectProxyDefaultScriptCommand")
    private List<String> connectProxyDefaultScriptCommand;

    @JsonProperty("ConnectReplicationToken")
    private String connectReplicationToken;

    @JsonProperty("ConnectSidecarMaxPort")
    private int connectSidecarMaxPort;

    @JsonProperty("ConnectSidecarMinPort")
    private int connectSidecarMinPort;

    @JsonProperty("ConnectTestDisableManagedProxies")
    private boolean connectTestDisableManagedProxies;

    @JsonProperty("ConsulCoordinateUpdateBatchSize")
    private int consulCoordinateUpdateBatchSize;

    @JsonProperty("ConsulCoordinateUpdateMaxBatches")
    private int consulCoordinateUpdateMaxBatches;

    @JsonProperty("ConsulCoordinateUpdatePeriod")
    private String consulCoordinateUpdatePeriod;

    @JsonProperty("ConsulRaftElectionTimeout")
    private String consulRaftElectionTimeout;

    @JsonProperty("ConsulRaftHeartbeatTimeout")
    private String consulRaftHeartbeatTimeout;

    @JsonProperty("ConsulRaftLeaderLeaseTimeout")
    private String consulRaftLeaderLeaseTimeout;

    @JsonProperty("ConsulServerHealthInterval")
    private String consulServerHealthInterval;

    @JsonProperty("DNSARecordLimit")
    private int dnsARecordLimit;

    @JsonProperty("DNSAddrs")
    private List<URI> dnsAddrs;

    @JsonProperty("DNSAllowStale")
    private boolean dnsAllowStale;

    @JsonProperty("DNSDisableCompression")
    private boolean dnsDisableCompression;

    @JsonProperty("DNSDomain")
    private String dnsDomain;

    @JsonProperty("DNSEnableTruncate")
    private boolean dnsEnableTruncate;

    @JsonProperty("DNSMaxStale")
    private String dnsMaxStale;

    @JsonProperty("DNSNodeMetaTXT")
    private boolean dnsNodeMetaTXT;

    @JsonProperty("DNSNodeTTL")
    private String dnsNodeTTL;

    @JsonProperty("DNSOnlyPassing")
    private boolean dnsOnlyPassing;

    @JsonProperty("DNSPort")
    private int dnsPort;

    @JsonProperty("DNSRecursorTimeout")
    private String dnsRecursorTimeout;

    @JsonProperty("DNSRecursors")
    private List<String> dnsRecursors;

    @JsonProperty("DNSSOA")
    private DNSSOA dnsSOA;

    @JsonProperty("DNSServiceTTL")
    private Map<String, String> dnsServiceTTL;

    @JsonProperty("DNSUDPAnswerLimit")
    private int dnsUDPAnswerLimit;

    @JsonProperty("DataDir")
    private String dataDir;

    @JsonProperty("Datacenter")
    private String datacenter;

    @JsonProperty("DevMode")
    private boolean devMode;

    @JsonProperty("DisableAnonymousSignature")
    private boolean disableAnonymousSignature;

    @JsonProperty("DisableCoordinates")
    private boolean disableCoordinates;

    @JsonProperty("DisableHTTPUnprintableCharFilter")
    private boolean disableHTTPUnprintableCharFilter;

    @JsonProperty("DisableHostNodeID")
    private boolean disableHostNodeID;

    @JsonProperty("DisableKeyringFile")
    private boolean disableKeyringFile;

    @JsonProperty("DisableRemoteExec")
    private boolean disableRemoteExec;

    @JsonProperty("DisableUpdateCheck")
    private boolean disableUpdateCheck;

    @JsonProperty("DiscardCheckOutput")
    private boolean discardCheckOutput;

    @JsonProperty("DiscoveryMaxStale")
    private String discoveryMaxStale;

    @JsonProperty("EnableAgentTLSForChecks")
    private boolean enableAgentTLSForChecks;

    @JsonProperty("EnableDebug")
    private boolean enableDebug;

    @JsonProperty("EnableLocalScriptChecks")
    private boolean enableLocalScriptChecks;

    @JsonProperty("EnableRemoteScriptChecks")
    private boolean enableRemoteScriptChecks;

    @JsonProperty("EnableSyslog")
    private boolean enableSyslog;

    @JsonProperty("EnableUI")
    private boolean enableUI;

    @JsonProperty("EncryptKey")
    private String encryptKey;

    @JsonProperty("EncryptVerifyIncoming")
    private boolean encryptVerifyIncoming;

    @JsonProperty("EncryptVerifyOutgoing")
    private boolean encryptVerifyOutgoing;

    @JsonProperty("GRPCAddrs")
    private List<URI> grpcAddrs;

    @JsonProperty("GRPCPort")
    private int grpcPort;

    @JsonProperty("GossipLANGossipInterval")
    private String gossipLANGossipInterval;

    @JsonProperty("GossipLANGossipNodes")
    private int gossipLANGossipNodes;

    @JsonProperty("GossipLANProbeInterval")
    private String gossipLANProbeInterval;

    @JsonProperty("GossipLANProbeTimeout")
    private String gossipLANProbeTimeout;

    @JsonProperty("GossipLANRetransmitMult")
    private int gossipLANRetransmitMult;

    @JsonProperty("GossipLANSuspicionMult")
    private int gossipLANSuspicionMult;

    @JsonProperty("GossipWANGossipInterval")
    private String gossipWANGossipInterval;

    @JsonProperty("GossipWANGossipNodes")
    private int gossipWANGossipNodes;

    @JsonProperty("GossipWANProbeInterval")
    private String gossipWANProbeInterval;

    @JsonProperty("GossipWANProbeTimeout")
    private String gossipWANProbeTimeout;

    @JsonProperty("GossipWANRetransmitMult")
    private int gossipWANRetransmitMult;

    @JsonProperty("GossipWANSuspicionMult")
    private int gossipWANSuspicionMult;

    @JsonProperty("HTTPAddrs")
    private List<URI> httpAddrs;

    @JsonProperty("HTTPBlockEndpoints")
    private List<String> httpBlockEndpoints;

    @JsonProperty("HTTPPort")
    private int httpPort;

    @JsonProperty("HTTPResponseHeaders")
    private Map<String, String> httpResponseHeaders;

    @JsonProperty("HTTPSAddrs")
    private List<URI> httpsAddrs;

    @JsonProperty("HTTPSPort")
    private int httpsPort;

    @JsonProperty("KeyFile")
    private String keyFile;

    @JsonProperty("LeaveDrainTime")
    private String leaveDrainTime;

    @JsonProperty("LeaveOnTerm")
    private boolean leaveOnTerm;

    @JsonProperty("LogFile")
    private String logFile;

    @JsonProperty("LogLevel")
    private String logLevel;

    @JsonProperty("LogRotateBytes")
    private int logRotateBytes;

    @JsonProperty("LogRotateDuration")
    private String logRotateDuration;

    @JsonProperty("NodeID")
    private String nodeID;

    @JsonProperty("NodeMeta")
    private Map<String, String> nodeMeta;

    @JsonProperty("NodeName")
    private String nodeName;

    @JsonProperty("NonVotingServer")
    private boolean nonVotingServer;

    @JsonProperty("PidFile")
    private String pidFile;

    @JsonProperty("PrimaryDatacenter")
    private String primaryDatacenter;

    @JsonProperty("RPCAdvertiseAddr")
    private URI rpcAdvertiseAddr;

    @JsonProperty("RPCBindAddr")
    private URI rpcBindAddr;

    @JsonProperty("RPCHoldTimeout")
    private String rpcHoldTimeout;

    @JsonProperty("RPCMaxBurst")
    private int rpcMaxBurst;

    @JsonProperty("RPCProtocol")
    private int rpcProtocol;

    @JsonProperty("RPCRateLimit")
    private int rpcRateLimit;

    @JsonProperty("RaftProtocol")
    private int raftProtocol;

    @JsonProperty("RaftSnapshotInterval")
    private String raftSnapshotInterval;

    @JsonProperty("RaftSnapshotThreshold")
    private int raftSnapshotThreshold;

    @JsonProperty("ReconnectTimeoutLAN")
    private String reconnectTimeoutLAN;

    @JsonProperty("ReconnectTimeoutWAN")
    private String reconnectTimeoutWAN;

    @JsonProperty("RejoinAfterLeave")
    private boolean rejoinAfterLeave;

    @JsonProperty("RetryJoinIntervalLAN")
    private String retryJoinIntervalLAN;

    @JsonProperty("RetryJoinIntervalWAN")
    private String retryJoinIntervalWAN;

    @JsonProperty("RetryJoinLAN")
    private List<String> retryJoinLAN;

    @JsonProperty("RetryJoinMaxAttemptsLAN")
    private int retryJoinMaxAttemptsLAN;

    @JsonProperty("RetryJoinMaxAttemptsWAN")
    private int retryJoinMaxAttemptsWAN;

    @JsonProperty("RetryJoinWAN")
    private List<String> retryJoinWAN;

    @JsonProperty("Revision")
    private String revision;

    @JsonProperty("SegmentLimit")
    private int segmentLimit;

    @JsonProperty("SegmentName")
    private String segmentName;

    @JsonProperty("SegmentNameLimit")
    private int segmentNameLimit;

    @JsonProperty("Segments")
    private List<NetworkSegment> segments;

    @JsonProperty("SerfAdvertiseAddrLAN")
    private URI serfAdvertiseAddrLAN;

    @JsonProperty("SerfAdvertiseAddrWAN")
    private URI serfAdvertiseAddrWAN;

    @JsonProperty("SerfBindAddrLAN")
    private URI serfBindAddrLAN;

    @JsonProperty("SerfBindAddrWAN")
    private URI serfBindAddrWAN;

    @JsonProperty("SerfPortLAN")
    private int serfPortLAN;

    @JsonProperty("SerfPortWAN")
    private int serfPortWAN;

    @JsonProperty("ServerMode")
    private boolean serverMode;

    @JsonProperty("ServerName")
    private String serverName;

    @JsonProperty("ServerPort")
    private int serverPort;
    // TODO: Services
    @JsonProperty("SessionTTLMin")
    private String sessionTTLMin;

    @JsonProperty("SkipLeaveOnInt")
    private boolean skipLeaveOnInt;

    @JsonProperty("StartJoinAddrsLAN")
    private List<String> startJoinAddrsLAN;

    @JsonProperty("StartJoinAddrsWAN")
    private List<String> startJoinAddrsWAN;

    @JsonProperty("SyncCoordinateIntervalMin")
    private String syncCoordinateIntervalMin;

    @JsonProperty("SyncCoordinateRateTarget")
    private int syncCoordinateRateTarget;

    @JsonProperty("SyslogFacility")
    private String syslogFacility;

    @JsonProperty("TLSCipherSuites")
    private List<Integer> tlsCipherSuites;

    @JsonProperty("TLSMinVersion")
    private String tlsMinVersion;

    @JsonProperty("TLSPreferServerCipherSuites")
    private boolean tlsPreferServerCipherSuites;

    @JsonProperty("TaggedAddresses")
    private TaggedAddresses taggedAddresses;

    @JsonProperty("Telemetry")
    private Telemetry telemetry;

    @JsonProperty("TranslateWANAddrs")
    private boolean translateWANAddrs;

    @JsonProperty("UIDir")
    private String uiDir;

    @JsonProperty("UnixSocketGroup")
    private String unixSocketGroup;

    @JsonProperty("UnixSocketMode")
    private String unixSocketMode;

    @JsonProperty("UnixSocketUser")
    private String unixSocketUser;

    @JsonProperty("VerifyIncoming")
    private boolean verifyIncoming;

    @JsonProperty("VerifyIncomingHTTPS")
    private boolean verifyIncomingHTTPS;

    @JsonProperty("VerifyIncomingRPC")
    private boolean verifyIncomingRPC;

    @JsonProperty("VerifyOutgoing")
    private boolean verifyOutgoing;

    @JsonProperty("VerifyServerHostname")
    private boolean verifyServerHostname;

    @JsonProperty("Version")
    private String version;

    @JsonProperty("VersionPrerelease")
    private String versionPrerelease;
    // TODO: Watches

    private Builder() {}

    public Builder withAclAgentMasterToken(String val) {
      aclAgentMasterToken = val;
      return this;
    }

    public Builder withAclAgentToken(String val) {
      aclAgentToken = val;
      return this;
    }

    public Builder withAclDatacenter(String val) {
      aclDatacenter = val;
      return this;
    }

    public Builder withAclDefaultPolicy(String val) {
      aclDefaultPolicy = val;
      return this;
    }

    public Builder withAclDisabledTTL(String val) {
      aclDisabledTTL = val;
      return this;
    }

    public Builder withAclDownPolicy(String val) {
      aclDownPolicy = val;
      return this;
    }

    public Builder withAclEnableKeyListPolicy(String val) {
      aclEnableKeyListPolicy = val;
      return this;
    }

    public Builder withAclEnforceVersion8(String val) {
      aclEnforceVersion8 = val;
      return this;
    }

    public Builder withAclMasterToken(String val) {
      aclMasterToken = val;
      return this;
    }

    public Builder withAclPolicyTTL(String val) {
      aclPolicyTTL = val;
      return this;
    }

    public Builder withAclReplicationToken(String val) {
      aclReplicationToken = val;
      return this;
    }

    public Builder withAclToken(String val) {
      aclToken = val;
      return this;
    }

    public Builder withAclTokenReplication(boolean val) {
      aclTokenReplication = val;
      return this;
    }

    public Builder withAclTokenTTL(String val) {
      aclTokenTTL = val;
      return this;
    }

    public Builder withAclsEnabled(boolean val) {
      aclsEnabled = val;
      return this;
    }

    public Builder withAeInterval(String val) {
      aeInterval = val;
      return this;
    }

    public Builder withAdvertiseAddrLan(String val) {
      advertiseAddrLan = val;
      return this;
    }

    public Builder withAdvertiseAddrWAN(String val) {
      advertiseAddrWAN = val;
      return this;
    }

    public Builder withAutopilotCleanupDeadServers(boolean val) {
      autopilotCleanupDeadServers = val;
      return this;
    }

    public Builder withAutopilotDisableUpgradeMigration(boolean val) {
      autopilotDisableUpgradeMigration = val;
      return this;
    }

    public Builder withAutopilotLastContactThreshold(String val) {
      autopilotLastContactThreshold = val;
      return this;
    }

    public Builder withAutopilotMaxTrailingLogs(int val) {
      autopilotMaxTrailingLogs = val;
      return this;
    }

    public Builder withAutopilotRedundancyZoneTag(String val) {
      autopilotRedundancyZoneTag = val;
      return this;
    }

    public Builder withAutopilotServerStabilizationTime(String val) {
      autopilotServerStabilizationTime = val;
      return this;
    }

    public Builder withAutopilotUpgradeVersionTag(String val) {
      autopilotUpgradeVersionTag = val;
      return this;
    }

    public Builder withBindAddr(String val) {
      bindAddr = val;
      return this;
    }

    public Builder withBootstrap(boolean val) {
      bootstrap = val;
      return this;
    }

    public Builder withBootstrapExpect(int val) {
      bootstrapExpect = val;
      return this;
    }

    public Builder withCaFile(String val) {
      caFile = val;
      return this;
    }

    public Builder withCaPath(String val) {
      caPath = val;
      return this;
    }

    public Builder withCertFile(String val) {
      certFile = val;
      return this;
    }

    public Builder withCheckDeregisterIntervalMin(String val) {
      checkDeregisterIntervalMin = val;
      return this;
    }

    public Builder withCheckReapInterval(String val) {
      checkReapInterval = val;
      return this;
    }

    public Builder withCheckUpdateInterval(String val) {
      checkUpdateInterval = val;
      return this;
    }

    public Builder withClientAddrs(List<String> val) {
      clientAddrs = val;
      return this;
    }

    public Builder withConnectEnabled(boolean val) {
      connectEnabled = val;
      return this;
    }

    public Builder withConnectProxyAllowManagedAPIRegistration(boolean val) {
      connectProxyAllowManagedAPIRegistration = val;
      return this;
    }

    public Builder withConnectProxyAllowManagedRoot(boolean val) {
      connectProxyAllowManagedRoot = val;
      return this;
    }

    public Builder withConnectProxyBindMaxPort(int val) {
      connectProxyBindMaxPort = val;
      return this;
    }

    public Builder withConnectProxyBindMinPort(int val) {
      connectProxyBindMinPort = val;
      return this;
    }

    public Builder withConnectProxyDefaultDaemonCommand(List<String> val) {
      connectProxyDefaultDaemonCommand = val;
      return this;
    }

    public Builder withConnectProxyDefaultExecMode(String val) {
      connectProxyDefaultExecMode = val;
      return this;
    }

    public Builder withConnectProxyDefaultScriptCommand(List<String> val) {
      connectProxyDefaultScriptCommand = val;
      return this;
    }

    public Builder withConnectReplicationToken(String val) {
      connectReplicationToken = val;
      return this;
    }

    public Builder withConnectSidecarMaxPort(int val) {
      connectSidecarMaxPort = val;
      return this;
    }

    public Builder withConnectSidecarMinPort(int val) {
      connectSidecarMinPort = val;
      return this;
    }

    public Builder withConnectTestDisableManagedProxies(boolean val) {
      connectTestDisableManagedProxies = val;
      return this;
    }

    public Builder withConsulCoordinateUpdateBatchSize(int val) {
      consulCoordinateUpdateBatchSize = val;
      return this;
    }

    public Builder withConsulCoordinateUpdateMaxBatches(int val) {
      consulCoordinateUpdateMaxBatches = val;
      return this;
    }

    public Builder withConsulCoordinateUpdatePeriod(String val) {
      consulCoordinateUpdatePeriod = val;
      return this;
    }

    public Builder withConsulRaftElectionTimeout(String val) {
      consulRaftElectionTimeout = val;
      return this;
    }

    public Builder withConsulRaftHeartbeatTimeout(String val) {
      consulRaftHeartbeatTimeout = val;
      return this;
    }

    public Builder withConsulRaftLeaderLeaseTimeout(String val) {
      consulRaftLeaderLeaseTimeout = val;
      return this;
    }

    public Builder withConsulServerHealthInterval(String val) {
      consulServerHealthInterval = val;
      return this;
    }

    public Builder withDnsARecordLimit(int val) {
      dnsARecordLimit = val;
      return this;
    }

    public Builder withDnsAddrs(List<URI> val) {
      dnsAddrs = val;
      return this;
    }

    public Builder withDnsAllowStale(boolean val) {
      dnsAllowStale = val;
      return this;
    }

    public Builder withDnsDisableCompression(boolean val) {
      dnsDisableCompression = val;
      return this;
    }

    public Builder withDnsDomain(String val) {
      dnsDomain = val;
      return this;
    }

    public Builder withDnsEnableTruncate(boolean val) {
      dnsEnableTruncate = val;
      return this;
    }

    public Builder withDnsMaxStale(String val) {
      dnsMaxStale = val;
      return this;
    }

    public Builder withDnsNodeMetaTXT(boolean val) {
      dnsNodeMetaTXT = val;
      return this;
    }

    public Builder withDnsNodeTTL(String val) {
      dnsNodeTTL = val;
      return this;
    }

    public Builder withDnsOnlyPassing(boolean val) {
      dnsOnlyPassing = val;
      return this;
    }

    public Builder withDnsPort(int val) {
      dnsPort = val;
      return this;
    }

    public Builder withDnsRecursorTimeout(String val) {
      dnsRecursorTimeout = val;
      return this;
    }

    public Builder withDnsRecursors(List<String> val) {
      dnsRecursors = val;
      return this;
    }

    public Builder withDnsSOA(DNSSOA val) {
      dnsSOA = val;
      return this;
    }

    public Builder withDnsServiceTTL(Map<String, String> val) {
      dnsServiceTTL = val;
      return this;
    }

    public Builder withDnsUDPAnswerLimit(int val) {
      dnsUDPAnswerLimit = val;
      return this;
    }

    public Builder withDataDir(String val) {
      dataDir = val;
      return this;
    }

    public Builder withDatacenter(String val) {
      datacenter = val;
      return this;
    }

    public Builder withDevMode(boolean val) {
      devMode = val;
      return this;
    }

    public Builder withDisableAnonymousSignature(boolean val) {
      disableAnonymousSignature = val;
      return this;
    }

    public Builder withDisableCoordinates(boolean val) {
      disableCoordinates = val;
      return this;
    }

    public Builder withDisableHTTPUnprintableCharFilter(boolean val) {
      disableHTTPUnprintableCharFilter = val;
      return this;
    }

    public Builder withDisableHostNodeID(boolean val) {
      disableHostNodeID = val;
      return this;
    }

    public Builder withDisableKeyringFile(boolean val) {
      disableKeyringFile = val;
      return this;
    }

    public Builder withDisableRemoteExec(boolean val) {
      disableRemoteExec = val;
      return this;
    }

    public Builder withDisableUpdateCheck(boolean val) {
      disableUpdateCheck = val;
      return this;
    }

    public Builder withDiscardCheckOutput(boolean val) {
      discardCheckOutput = val;
      return this;
    }

    public Builder withDiscoveryMaxStale(String val) {
      discoveryMaxStale = val;
      return this;
    }

    public Builder withEnableAgentTLSForChecks(boolean val) {
      enableAgentTLSForChecks = val;
      return this;
    }

    public Builder withEnableDebug(boolean val) {
      enableDebug = val;
      return this;
    }

    public Builder withEnableLocalScriptChecks(boolean val) {
      enableLocalScriptChecks = val;
      return this;
    }

    public Builder withEnableRemoteScriptChecks(boolean val) {
      enableRemoteScriptChecks = val;
      return this;
    }

    public Builder withEnableSyslog(boolean val) {
      enableSyslog = val;
      return this;
    }

    public Builder withEnableUI(boolean val) {
      enableUI = val;
      return this;
    }

    public Builder withEncryptKey(String val) {
      encryptKey = val;
      return this;
    }

    public Builder withEncryptVerifyIncoming(boolean val) {
      encryptVerifyIncoming = val;
      return this;
    }

    public Builder withEncryptVerifyOutgoing(boolean val) {
      encryptVerifyOutgoing = val;
      return this;
    }

    public Builder withGrpcAddrs(List<URI> val) {
      grpcAddrs = val;
      return this;
    }

    public Builder withGrpcPort(int val) {
      grpcPort = val;
      return this;
    }

    public Builder withGossipLANGossipInterval(String val) {
      gossipLANGossipInterval = val;
      return this;
    }

    public Builder withGossipLANGossipNodes(int val) {
      gossipLANGossipNodes = val;
      return this;
    }

    public Builder withGossipLANProbeInterval(String val) {
      gossipLANProbeInterval = val;
      return this;
    }

    public Builder withGossipLANProbeTimeout(String val) {
      gossipLANProbeTimeout = val;
      return this;
    }

    public Builder withGossipLANRetransmitMult(int val) {
      gossipLANRetransmitMult = val;
      return this;
    }

    public Builder withGossipLANSuspicionMult(int val) {
      gossipLANSuspicionMult = val;
      return this;
    }

    public Builder withGossipWANGossipInterval(String val) {
      gossipWANGossipInterval = val;
      return this;
    }

    public Builder withGossipWANGossipNodes(int val) {
      gossipWANGossipNodes = val;
      return this;
    }

    public Builder withGossipWANProbeInterval(String val) {
      gossipWANProbeInterval = val;
      return this;
    }

    public Builder withGossipWANProbeTimeout(String val) {
      gossipWANProbeTimeout = val;
      return this;
    }

    public Builder withGossipWANRetransmitMult(int val) {
      gossipWANRetransmitMult = val;
      return this;
    }

    public Builder withGossipWANSuspicionMult(int val) {
      gossipWANSuspicionMult = val;
      return this;
    }

    public Builder withHttpAddrs(List<URI> val) {
      httpAddrs = val;
      return this;
    }

    public Builder withHttpBlockEndpoints(List<String> val) {
      httpBlockEndpoints = val;
      return this;
    }

    public Builder withHttpPort(int val) {
      httpPort = val;
      return this;
    }

    public Builder withHttpResponseHeaders(Map<String, String> val) {
      httpResponseHeaders = val;
      return this;
    }

    public Builder withHttpsAddrs(List<URI> val) {
      httpsAddrs = val;
      return this;
    }

    public Builder withHttpsPort(int val) {
      httpsPort = val;
      return this;
    }

    public Builder withKeyFile(String val) {
      keyFile = val;
      return this;
    }

    public Builder withLeaveDrainTime(String val) {
      leaveDrainTime = val;
      return this;
    }

    public Builder withLeaveOnTerm(boolean val) {
      leaveOnTerm = val;
      return this;
    }

    public Builder withLogFile(String val) {
      logFile = val;
      return this;
    }

    public Builder withLogLevel(String val) {
      logLevel = val;
      return this;
    }

    public Builder withLogRotateBytes(int val) {
      logRotateBytes = val;
      return this;
    }

    public Builder withLogRotateDuration(String val) {
      logRotateDuration = val;
      return this;
    }

    public Builder withNodeID(String val) {
      nodeID = val;
      return this;
    }

    public Builder withNodeMeta(Map<String, String> val) {
      nodeMeta = val;
      return this;
    }

    public Builder withNodeName(String val) {
      nodeName = val;
      return this;
    }

    public Builder withNonVotingServer(boolean val) {
      nonVotingServer = val;
      return this;
    }

    public Builder withPidFile(String val) {
      pidFile = val;
      return this;
    }

    public Builder withPrimaryDatacenter(String val) {
      primaryDatacenter = val;
      return this;
    }

    public Builder withRpcAdvertiseAddr(URI val) {
      rpcAdvertiseAddr = val;
      return this;
    }

    public Builder withRpcBindAddr(URI val) {
      rpcBindAddr = val;
      return this;
    }

    public Builder withRpcHoldTimeout(String val) {
      rpcHoldTimeout = val;
      return this;
    }

    public Builder withRpcMaxBurst(int val) {
      rpcMaxBurst = val;
      return this;
    }

    public Builder withRpcProtocol(int val) {
      rpcProtocol = val;
      return this;
    }

    public Builder withRpcRateLimit(int val) {
      rpcRateLimit = val;
      return this;
    }

    public Builder withRaftProtocol(int val) {
      raftProtocol = val;
      return this;
    }

    public Builder withRaftSnapshotInterval(String val) {
      raftSnapshotInterval = val;
      return this;
    }

    public Builder withRaftSnapshotThreshold(int val) {
      raftSnapshotThreshold = val;
      return this;
    }

    public Builder withReconnectTimeoutLAN(String val) {
      reconnectTimeoutLAN = val;
      return this;
    }

    public Builder withReconnectTimeoutWAN(String val) {
      reconnectTimeoutWAN = val;
      return this;
    }

    public Builder withRejoinAfterLeave(boolean val) {
      rejoinAfterLeave = val;
      return this;
    }

    public Builder withRetryJoinIntervalLAN(String val) {
      retryJoinIntervalLAN = val;
      return this;
    }

    public Builder withRetryJoinIntervalWAN(String val) {
      retryJoinIntervalWAN = val;
      return this;
    }

    public Builder withRetryJoinLAN(List<String> val) {
      retryJoinLAN = val;
      return this;
    }

    public Builder withRetryJoinMaxAttemptsLAN(int val) {
      retryJoinMaxAttemptsLAN = val;
      return this;
    }

    public Builder withRetryJoinMaxAttemptsWAN(int val) {
      retryJoinMaxAttemptsWAN = val;
      return this;
    }

    public Builder withRetryJoinWAN(List<String> val) {
      retryJoinWAN = val;
      return this;
    }

    public Builder withRevision(String val) {
      revision = val;
      return this;
    }

    public Builder withSegmentLimit(int val) {
      segmentLimit = val;
      return this;
    }

    public Builder withSegmentName(String val) {
      segmentName = val;
      return this;
    }

    public Builder withSegmentNameLimit(int val) {
      segmentNameLimit = val;
      return this;
    }

    public Builder withSegments(List<NetworkSegment> val) {
      segments = val;
      return this;
    }

    public Builder withSerfAdvertiseAddrLAN(URI val) {
      serfAdvertiseAddrLAN = val;
      return this;
    }

    public Builder withSerfAdvertiseAddrWAN(URI val) {
      serfAdvertiseAddrWAN = val;
      return this;
    }

    public Builder withSerfBindAddrLAN(URI val) {
      serfBindAddrLAN = val;
      return this;
    }

    public Builder withSerfBindAddrWAN(URI val) {
      serfBindAddrWAN = val;
      return this;
    }

    public Builder withSerfPortLAN(int val) {
      serfPortLAN = val;
      return this;
    }

    public Builder withSerfPortWAN(int val) {
      serfPortWAN = val;
      return this;
    }

    public Builder withServerMode(boolean val) {
      serverMode = val;
      return this;
    }

    public Builder withServerName(String val) {
      serverName = val;
      return this;
    }

    public Builder withServerPort(int val) {
      serverPort = val;
      return this;
    }

    public Builder withSessionTTLMin(String val) {
      sessionTTLMin = val;
      return this;
    }

    public Builder withSkipLeaveOnInt(boolean val) {
      skipLeaveOnInt = val;
      return this;
    }

    public Builder withStartJoinAddrsLAN(List<String> val) {
      startJoinAddrsLAN = val;
      return this;
    }

    public Builder withStartJoinAddrsWAN(List<String> val) {
      startJoinAddrsWAN = val;
      return this;
    }

    public Builder withSyncCoordinateIntervalMin(String val) {
      syncCoordinateIntervalMin = val;
      return this;
    }

    public Builder withSyncCoordinateRateTarget(int val) {
      syncCoordinateRateTarget = val;
      return this;
    }

    public Builder withSyslogFacility(String val) {
      syslogFacility = val;
      return this;
    }

    public Builder withTlsCipherSuites(List<Integer> val) {
      tlsCipherSuites = val;
      return this;
    }

    public Builder withTlsMinVersion(String val) {
      tlsMinVersion = val;
      return this;
    }

    public Builder withTlsPreferServerCipherSuites(boolean val) {
      tlsPreferServerCipherSuites = val;
      return this;
    }

    public Builder withTaggedAddresses(TaggedAddresses val) {
      taggedAddresses = val;
      return this;
    }

    public Builder withTelemetry(Telemetry val) {
      telemetry = val;
      return this;
    }

    public Builder withTranslateWANAddrs(boolean val) {
      translateWANAddrs = val;
      return this;
    }

    public Builder withUiDir(String val) {
      uiDir = val;
      return this;
    }

    public Builder withUnixSocketGroup(String val) {
      unixSocketGroup = val;
      return this;
    }

    public Builder withUnixSocketMode(String val) {
      unixSocketMode = val;
      return this;
    }

    public Builder withUnixSocketUser(String val) {
      unixSocketUser = val;
      return this;
    }

    public Builder withVerifyIncoming(boolean val) {
      verifyIncoming = val;
      return this;
    }

    public Builder withVerifyIncomingHTTPS(boolean val) {
      verifyIncomingHTTPS = val;
      return this;
    }

    public Builder withVerifyIncomingRPC(boolean val) {
      verifyIncomingRPC = val;
      return this;
    }

    public Builder withVerifyOutgoing(boolean val) {
      verifyOutgoing = val;
      return this;
    }

    public Builder withVerifyServerHostname(boolean val) {
      verifyServerHostname = val;
      return this;
    }

    public Builder withVersion(String val) {
      version = val;
      return this;
    }

    public Builder withVersionPrerelease(String val) {
      versionPrerelease = val;
      return this;
    }

    public RuntimeConfig build() {
      return new RuntimeConfig(this);
    }
  }
}
