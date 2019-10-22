package org.coody.framework.core.system;

import java.net.InetAddress;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.coody.framework.context.base.BaseLogger;
import org.coody.framework.context.base.BaseModel;
import org.coody.framework.util.EncryptUtil;
import org.coody.framework.util.PrintException;
import org.coody.framework.util.StringUtil;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.Who;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class SystemHandle implements InitializingBean {

	private static final BaseLogger logger = BaseLogger.getLoggerPro(SystemHandle.class);

	// 本项目运行目录
	public static String rootPath;
	// 操作系统信息
	public static SystemInfo systemInfo;
	// 用户信息
	public static PCUserInfo pcUserInfo;
	// 内存信息
	public static MemoryModel memoryModel;
	// 服务器标识
	public static String serverId;

	// 获取磁盘信息
	public static List<DiskInfo> getDiskInfos() {
		try {
			Sigar sigar = new Sigar();
			FileSystem fslist[] = sigar.getFileSystemList();
			if (StringUtil.isNullOrEmpty(fslist)) {
				return null;
			}
			List<DiskInfo> infos = new ArrayList<SystemHandle.DiskInfo>();
			for (int i = 0; i < fslist.length; i++) {
				try {
					FileSystem fs = fslist[i];
					FileSystemUsage usage = sigar.getFileSystemUsage(fs.getDirName());
					DiskInfo disk = new DiskInfo();
					disk.setAvail(usage.getAvail() / 1024L);
					disk.setDevName(fs.getDevName());
					disk.setDirName(fs.getDirName());
					disk.setFree(usage.getFree() / 1024L);
					disk.setSysTypeName(fs.getSysTypeName());
					disk.setTotal(usage.getTotal() / 1024L);
					disk.setUsed(usage.getUsed() / 1024L);
					double usePercent = usage.getUsePercent() * 100D;
					disk.setUsePercent(usePercent);
					infos.add(disk);
				} catch (Exception e) {
				}
			}
			return infos;
		} catch (Exception e) {
			return null;
		}
	}

	public static JavaInfo getJavaInfo() {
		return new JavaInfo();
	}

	// 获取CPU使用信息
	public static List<CpuModel> getCpuInfos() {
		try {
			Sigar sigar = new Sigar();
			CpuInfo infos[] = sigar.getCpuInfoList();
			CpuPerc cpuList[] = null;
			cpuList = sigar.getCpuPercList();
			if (StringUtil.isNullOrEmpty(infos)) {
				return null;
			}
			List<CpuModel> cpus = new ArrayList<CpuModel>();
			for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
				CpuInfo info = infos[i];
				CpuModel model = new CpuModel();
				model.setCacheSize(info.getCacheSize());
				model.setCombined(CpuPerc.format(cpuList[i].getCombined()));
				model.setIdle(CpuPerc.format(cpuList[i].getIdle()));
				model.setMhz(info.getMhz());
				model.setModel(info.getModel());
				model.setVendor(info.getVendor());
				cpus.add(model);
			}
			return cpus;
		} catch (Exception e) {
			return null;
		}
	}

	// 获取JAVA信息
	public static class JavaInfo {
		// java总内存
		private Long javaTotalMemory;
		// java剩余内存
		private Long javaFreeMemory;
		// java可用处理器数目
		private Integer availableProcessors;
		// java版本
		private String javaVersion;

		public JavaInfo() {
			Runtime r = Runtime.getRuntime();
			Properties props = System.getProperties();
			this.javaTotalMemory = r.maxMemory() / 1024L / 1024L;
			this.javaFreeMemory = r.freeMemory() / 1024L / 1024L;
			this.availableProcessors = r.availableProcessors();
			this.javaVersion = StringUtil.toString(props.getProperty("java.version"));
		}

		public Long getJavaTotalMemory() {
			return javaTotalMemory;
		}

		public void setJavaTotalMemory(Long javaTotalMemory) {
			this.javaTotalMemory = javaTotalMemory;
		}

		public Long getJavaFreeMemory() {
			return javaFreeMemory;
		}

		public void setJavaFreeMemory(Long javaFreeMemory) {
			this.javaFreeMemory = javaFreeMemory;
		}

		public Integer getAvailableProcessors() {
			return availableProcessors;
		}

		public void setAvailableProcessors(Integer availableProcessors) {
			this.availableProcessors = availableProcessors;
		}

		public String getJavaVersion() {
			return javaVersion;
		}

		public void setJavaVersion(String javaVersion) {
			this.javaVersion = javaVersion;
		}

	}

	// 获取网卡信息
	public static List<NetModel> getNetModels() {
		try {
			Sigar sigar = new Sigar();
			String ifNames[] = sigar.getNetInterfaceList();
			if (StringUtil.isNullOrEmpty(ifNames)) {
				return null;
			}
			List<NetModel> nets = new ArrayList<NetModel>();
			for (int i = 0; i < ifNames.length; i++) {
				try {
					String name = ifNames[i];
					NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
					if (NetFlags.LOOPBACK_ADDRESS.equals(ifconfig.getAddress())
							|| (ifconfig.getFlags() & NetFlags.IFF_LOOPBACK) != 0
							|| NetFlags.NULL_HWADDR.equals(ifconfig.getHwaddr())) {
						continue;
					}
					if (StringUtil.isNullOrEmpty(ifconfig.getAddress()) || "0.0.0.0".equals(ifconfig.getAddress())
							|| "127.0.0.1".equals(ifconfig.getAddress())) {
						continue;
					}
					NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
					NetModel net = new NetModel();
					net.setEquaName(name);
					net.setIpAddress(ifconfig.getAddress());
					net.setRxBytes(ifstat.getRxPackets());
					net.setRxDropped(ifstat.getRxDropped());
					net.setRxErrors(ifstat.getRxErrors());
					net.setRxPackets(ifstat.getRxPackets());
					net.setTxBytes(ifstat.getTxPackets());
					net.setTxDropped(ifstat.getTxDropped());
					net.setTxErrors(ifstat.getTxErrors());
					net.setTxPackets(ifstat.getTxPackets());
					net.setBroadcast(ifconfig.getBroadcast());
					net.setHwaddr(ifconfig.getHwaddr());
					nets.add(net);
				} catch (Exception e) {
				}
			}
			return nets;
		} catch (Exception e) {
			return null;
		}
	}

	public static String getServerId() {
		// 计算机名、部署路径、网卡mac、ip地址
		String driverSign = MessageFormat.format("{0}-{1}-{2}-{3}", systemInfo.pcName, rootPath, getMacs(),
				getIpAddress());
		return EncryptUtil.customEnCode(driverSign);
	}

	private static String getMacs() {
		try {
			StringBuffer sb = new StringBuffer();
			Sigar sigar = new Sigar();
			String[] ifaces = sigar.getNetInterfaceList();
			for (int i = 0; i < ifaces.length; i++) {
				NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
				if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress()) || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
						|| NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
					continue;
				}
				if (!StringUtil.isNullOrEmpty(sb.toString())) {
					sb.append(";");
				}
				sb.append(cfg.getHwaddr());
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		}

	}

	private static String getIpAddress() {
		try {
			StringBuffer sb = new StringBuffer();
			Sigar sigar = new Sigar();
			String ifNames[] = sigar.getNetInterfaceList();
			for (int i = 0; i < ifNames.length; i++) {
				String name = ifNames[i];
				NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
				if (ifconfig.getAddress() == null || "0.0.0.0".equals(ifconfig.getAddress())
						|| "127.0.0.1".equals(ifconfig.getAddress())) {
					continue;
				}
				if (!StringUtil.isNullOrEmpty(sb.toString())) {
					sb.append(";");
				}
				sb.append(ifconfig.getAddress());
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		}
	}

	// 网卡信息
	public static class NetModel {
		// 设备名称
		private String equaName;
		// IP地址
		private String ipAddress;
		// 接受包总数(单位均为字节)
		private Long rxPackets;
		// 发包总数
		private Long txPackets;
		// 收包总字节数
		private Long rxBytes;
		// 发包总字节数
		private Long txBytes;
		// 收包错误数
		private Long rxErrors;
		// 发包错误数
		private Long txErrors;
		// 收包丢弃量
		private Long rxDropped;
		// 发包丢弃量
		private Long txDropped;
		// 广播地址
		private String broadcast;
		// Mac地址
		private String hwaddr;

		public String getBroadcast() {
			return broadcast;
		}

		public void setBroadcast(String broadcast) {
			this.broadcast = broadcast;
		}

		public String getHwaddr() {
			return hwaddr;
		}

		public void setHwaddr(String hwaddr) {
			this.hwaddr = hwaddr;
		}

		public String getEquaName() {
			return equaName;
		}

		public void setEquaName(String equaName) {
			this.equaName = equaName;
		}

		public String getIpAddress() {
			return ipAddress;
		}

		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}

		public Long getRxPackets() {
			return rxPackets;
		}

		public void setRxPackets(Long rxPackets) {
			this.rxPackets = rxPackets;
		}

		public Long getTxPackets() {
			return txPackets;
		}

		public void setTxPackets(Long txPackets) {
			this.txPackets = txPackets;
		}

		public Long getRxBytes() {
			return rxBytes;
		}

		public void setRxBytes(Long rxBytes) {
			this.rxBytes = rxBytes;
		}

		public Long getTxBytes() {
			return txBytes;
		}

		public void setTxBytes(Long txBytes) {
			this.txBytes = txBytes;
		}

		public Long getRxErrors() {
			return rxErrors;
		}

		public void setRxErrors(Long rxErrors) {
			this.rxErrors = rxErrors;
		}

		public Long getTxErrors() {
			return txErrors;
		}

		public void setTxErrors(Long txErrors) {
			this.txErrors = txErrors;
		}

		public Long getRxDropped() {
			return rxDropped;
		}

		public void setRxDropped(Long rxDropped) {
			this.rxDropped = rxDropped;
		}

		public Long getTxDropped() {
			return txDropped;
		}

		public void setTxDropped(Long txDropped) {
			this.txDropped = txDropped;
		}

	}

	// CPU信息
	@SuppressWarnings("serial")
	public static class CpuModel extends BaseModel {
		// 空闲率
		private String idle;
		// 总使用率
		private String combined;
		// 主频
		private Integer mhz;
		// 厂商
		private String vendor;
		// 类别
		private String model;
		// 缓存(单位字节)
		private Long cacheSize;

		public String getIdle() {
			return idle;
		}

		public void setIdle(String idle) {
			this.idle = idle;
		}

		public String getCombined() {
			return combined;
		}

		public void setCombined(String combined) {
			this.combined = combined;
		}

		public Integer getMhz() {
			return mhz;
		}

		public void setMhz(Integer mhz) {
			this.mhz = mhz;
		}

		public String getVendor() {
			return vendor;
		}

		public void setVendor(String vendor) {
			this.vendor = vendor;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public Long getCacheSize() {
			return cacheSize;
		}

		public void setCacheSize(Long cacheSize) {
			this.cacheSize = cacheSize;
		}
	}

	// 磁盘信息
	public static class DiskInfo {
		// 盘符名称
		private String devName;
		// 盘符路径
		private String dirName;
		// 盘符类型
		private String sysTypeName;
		// 磁盘总大小(单位均为m)
		private Long total;
		// 磁盘剩余空间
		private Long free;
		// 磁盘可用空间
		private Long avail;
		// 磁盘已使用空间
		private Long used;
		// 磁盘利用率百分比
		private Double usePercent;

		public String getDevName() {
			return devName;
		}

		public void setDevName(String devName) {
			this.devName = devName;
		}

		public String getDirName() {
			return dirName;
		}

		public void setDirName(String dirName) {
			this.dirName = dirName;
		}

		public String getSysTypeName() {
			return sysTypeName;
		}

		public void setSysTypeName(String sysTypeName) {
			this.sysTypeName = sysTypeName;
		}

		public Long getTotal() {
			return total;
		}

		public void setTotal(Long total) {
			this.total = total;
		}

		public Long getFree() {
			return free;
		}

		public void setFree(Long free) {
			this.free = free;
		}

		public Long getAvail() {
			return avail;
		}

		public void setAvail(Long avail) {
			this.avail = avail;
		}

		public Long getUsed() {
			return used;
		}

		public void setUsed(Long used) {
			this.used = used;
		}

		public Double getUsePercent() {
			return usePercent;
		}

		public void setUsePercent(Double usePercent) {
			this.usePercent = usePercent;
		}

	}

	// 内存信息
	@SuppressWarnings("serial")
	public static class MemoryModel extends BaseModel {
		// 总内存(单位均为m)
		private Long total;
		// 已经使用的内存
		private Long used;
		// 空闲内存
		private Long free;
		// 交换区内存
		private Long swapTotal;
		// 交换区使用量
		private Long swapUsed;
		// 交换区空闲
		private Long swapFree;

		public MemoryModel() {
			try {
				Sigar sigar = new Sigar();
				Mem mem = sigar.getMem();
				Swap swap = sigar.getSwap();
				this.total = mem.getTotal() / 1024L / 1024L;
				this.swapFree = swap.getFree() / 1024L / 1024L;
				this.free = mem.getFree() / 1024L / 1024L;
				this.swapTotal = swap.getTotal() / 1024L / 1024L;
				this.swapUsed = swap.getUsed() / 1024L / 1024;
				this.used = mem.getUsed() / 1024L / 1024L;
			} catch (Exception e) {
			}
		}

		public Long getTotal() {
			return total;
		}

		public void setTotal(Long total) {
			this.total = total;
		}

		public Long getUsed() {
			return used;
		}

		public void setUsed(Long used) {
			this.used = used;
		}

		public Long getFree() {
			return free;
		}

		public void setFree(Long free) {
			this.free = free;
		}

		public Long getSwapTotal() {
			return swapTotal;
		}

		public void setSwapTotal(Long swapTotal) {
			this.swapTotal = swapTotal;
		}

		public Long getSwapUsed() {
			return swapUsed;
		}

		public void setSwapUsed(Long swapUsed) {
			this.swapUsed = swapUsed;
		}

		public Long getSwapFree() {
			return swapFree;
		}

		public void setSwapFree(Long swapFree) {
			this.swapFree = swapFree;
		}
	}

	// 用户信息
	@SuppressWarnings("serial")
	public static class PCUserInfo extends BaseModel {
		// 用户名
		private String userName;
		// 用户主目录
		private String userHome;
		// 用户当前目录
		private String userDir;

		public PCUserInfo() {
			try {
				Properties props = System.getProperties();
				this.userName = getPcUserName();
				this.userHome = StringUtil.toString(props.getProperty("user.home"));
				this.userDir = StringUtil.toString(props.getProperty("user.dir"));
			} catch (Exception e) {
			}
		}

		public String getUserName() {
			if (StringUtil.isNullOrEmpty(userName)) {
				userName = getPcUserName();
			}
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getUserHome() {
			return userHome;
		}

		public void setUserHome(String userHome) {
			this.userHome = userHome;
		}

		public String getUserDir() {
			return userDir;
		}

		public void setUserDir(String userDir) {
			this.userDir = userDir;
		}

	}

	private static String getPcUserName() {
		try {
			Sigar sigar = new Sigar();
			Who who[] = sigar.getWhoList();
			Set<String> userNames = new HashSet<String>();
			if (who != null && who.length > 0) {
				for (int i = 0; i < who.length; i++) {
					Who _who = who[i];
					userNames.add(_who.getUser());
				}
			}
			return StringUtil.collectionMosaic(new ArrayList<String>(userNames), ",");
		} catch (Exception e) {
			return null;
		}
	}

	// 系统信息
	@SuppressWarnings("serial")
	public static class SystemInfo extends BaseModel {
		// 系统名称
		private String pcName;
		// 系统名称
		private String osName;
		// 系统架构
		private String osArch;
		// 系统版本
		private String osVersion;

		public SystemInfo() {
			try {
				Properties props = System.getProperties();
				this.pcName = getPCName();
				this.osName = StringUtil.toString(props.getProperty("os.name"));
				this.osArch = StringUtil.toString(props.getProperty("os.arch"));
				this.osVersion = StringUtil.toString(props.getProperty("os.version"));
			} catch (Exception e) {
			}
		}

		private static String getPCName() {
			InetAddress addr = null;
			String address = "";
			try {
				addr = InetAddress.getLocalHost();// 新建一个InetAddress类
				address = addr.getHostName().toString();// 获得本机名称
			} catch (Exception e) {
				PrintException.printException(logger, e);
			}
			return address;
		}

		public String getPcName() {
			return pcName;
		}

		public void setPcName(String pcName) {
			this.pcName = pcName;
		}

		public String getOsName() {
			return osName;
		}

		public void setOsName(String osName) {
			this.osName = osName;
		}

		public String getOsArch() {
			return osArch;
		}

		public void setOsArch(String osArch) {
			this.osArch = osArch;
		}

		public String getOsVersion() {
			return osVersion;
		}

		public void setOsVersion(String osVersion) {
			this.osVersion = osVersion;
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		rootPath = new SystemHandle().getClass().getResource("/").getFile().toString();
		systemInfo = new SystemInfo();
		pcUserInfo = new PCUserInfo();
		memoryModel = new MemoryModel();
		serverId = getServerId();
	}

}
